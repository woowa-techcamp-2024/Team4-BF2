package woowa.team4.bff.api.client.advertisement.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdvertisementResponse {

    private long restaurantId;
    private int adRank;
    private boolean hasAdvertisement;
}
