package woowa.team4.bff.api.client.delivery.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryTimeResponse {

    private long restaurantId;
    private int min;
    private int max;
}
