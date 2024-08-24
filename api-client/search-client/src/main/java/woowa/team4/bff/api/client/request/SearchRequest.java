package woowa.team4.bff.api.client.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SearchRequest {

    private String keyword;
    private String deliveryLocation;
    private Integer pageNumber;
}
