package woowa.team4.bff.api.client.advertisement.caller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import woowa.team4.bff.api.client.advertisement.config.AdvertisementClientConfiguration;
import woowa.team4.bff.api.client.advertisement.request.AdvertisementRequest;
import woowa.team4.bff.api.client.advertisement.response.AdvertisementResponse;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;

@Component
@RequiredArgsConstructor
public class AdvertisementApiCaller {

    private final AdvertisementClientConfiguration advertisementClientConfiguration;
    private final SyncClientApiCaller syncClientApiCaller;

    public List<AdvertisementResponse> send(AdvertisementRequest advertisementRequest) {
        return syncClientApiCaller.post(advertisementClientConfiguration.getUrl(),
                advertisementRequest,
                new ParameterizedTypeReference<>() {
                });
    }
}
