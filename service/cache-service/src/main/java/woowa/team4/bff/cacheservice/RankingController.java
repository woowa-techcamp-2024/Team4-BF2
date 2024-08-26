package woowa.team4.bff.cacheservice;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.api.client.cache.request.RankingRequest;
import woowa.team4.bff.cache.redis.domain.SearchRankingResponse;
import woowa.team4.bff.cache.redis.service.RankingService;

@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @PostMapping("")
    public void rankUp(@RequestBody RankingRequest request){
        rankingService.incrementSearchCount(request.getKeyword());
    }

    @GetMapping("")
    public List<SearchRankingResponse> getRank(){
        return rankingService.getTop10SearchKeywords();
    }
}
