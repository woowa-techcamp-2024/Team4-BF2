package woowa.team4.bff.api.client.advertisement.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AdvertisementRequest {

    private List<Long> ids;
    private String keyword;
}
