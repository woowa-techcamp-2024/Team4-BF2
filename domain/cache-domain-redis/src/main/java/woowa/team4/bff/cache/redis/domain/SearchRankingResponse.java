package woowa.team4.bff.cache.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SearchRankingResponse {
    private int rank;
    private String keyword;
}
