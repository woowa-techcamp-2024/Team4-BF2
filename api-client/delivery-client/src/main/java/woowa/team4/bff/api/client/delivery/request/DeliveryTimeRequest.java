package woowa.team4.bff.api.client.delivery.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DeliveryTimeRequest {

    private List<Long> ids;
}
