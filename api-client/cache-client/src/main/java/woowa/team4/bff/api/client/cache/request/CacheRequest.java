package woowa.team4.bff.api.client.cache.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CacheRequest {

    private List<Long> ids;
}
