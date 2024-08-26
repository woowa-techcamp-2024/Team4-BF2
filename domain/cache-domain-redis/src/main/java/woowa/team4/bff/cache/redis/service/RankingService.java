package woowa.team4.bff.cache.redis.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import woowa.team4.bff.cache.redis.domain.SearchRankingResponse;


@Service
@RequiredArgsConstructor
public class RankingService {
    private final RedisTemplate<String, String> redisTemplate;
    private final String RANKING_KEY_PREFIX = "search_ranking:";
    private final long RANKING_EXPIRATION = 30; // 30분

    private String getCurrentIntervalKey() {
        LocalDateTime now = LocalDateTime.now();
        int minutes = now.getMinute();
        // 분의 10분 자리를 남기고 10분을 뺀다
        int adjustedMinutes = minutes - (minutes % 10);
        return RANKING_KEY_PREFIX + now.minusMinutes(10)
                .withMinute(adjustedMinutes)
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    private String getNextIntervalKey() {
        LocalDateTime now = LocalDateTime.now();
        int minutes = now.getMinute();
        // 분의 10의 자리만 남긴다
        int adjustedMinutes = minutes - (minutes % 10);
        return RANKING_KEY_PREFIX + now
                .withMinute(adjustedMinutes)
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
    }

    @Scheduled(cron = "0 0/10 * * * *") // 10분마다 실행
    public void switchRankingKey() {
        String currentIntervalKey = getCurrentIntervalKey();
        // 현재 간격 키에 대한 만료 시간 설정
        redisTemplate.expire(currentIntervalKey, RANKING_EXPIRATION, TimeUnit.MINUTES);
    }

    public void incrementSearchCount(String keyword) {
        String nextIntervalKey = getNextIntervalKey();
        redisTemplate.opsForZSet().incrementScore(nextIntervalKey, keyword, 1);
    }

    public List<SearchRankingResponse> getTop10SearchKeywords() {
        String currentIntervalKey = getNextIntervalKey();
        Set<ZSetOperations.TypedTuple<String>> typedTuples =
                redisTemplate.opsForZSet().reverseRangeWithScores(currentIntervalKey, 0, 9);

        List<SearchRankingResponse> result = new ArrayList<>();
        int rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            result.add(new SearchRankingResponse(rank++, tuple.getValue()));
        }
        return result;
    }
}
