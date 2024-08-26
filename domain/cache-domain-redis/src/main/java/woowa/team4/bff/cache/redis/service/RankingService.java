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
    private final long RANKING_EXPIRATION = 2; // 1시간

    private String getCurrentHourKey() {
        return RANKING_KEY_PREFIX + LocalDateTime.now().minusHours(1).format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }

    private String getNextHourKey() {
        return RANKING_KEY_PREFIX + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }

    @Scheduled(cron = "0 0 * * * *") // 매시 정각에 실행
    public void switchRankingKey() {
        String currentHourKey = getCurrentHourKey();
        // 현재 시간 키에 대한 만료 시간 설정
        redisTemplate.expire(currentHourKey, RANKING_EXPIRATION, TimeUnit.HOURS);
    }

    public void incrementSearchCount(String keyword) {
        String nextHourKey = getNextHourKey();
        redisTemplate.opsForZSet().incrementScore(nextHourKey, keyword, 1);
    }

    public List<SearchRankingResponse> getTop10SearchKeywords() {
        String currentHourKey = getNextHourKey();
        Set<ZSetOperations.TypedTuple<String>> typedTuples =
                redisTemplate.opsForZSet().reverseRangeWithScores(currentHourKey, 0, 9);

        List<SearchRankingResponse> result = new ArrayList<>();
        int rank = 1;
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            result.add(new SearchRankingResponse(rank++, tuple.getValue()));
        }
        return result;
    }
}
