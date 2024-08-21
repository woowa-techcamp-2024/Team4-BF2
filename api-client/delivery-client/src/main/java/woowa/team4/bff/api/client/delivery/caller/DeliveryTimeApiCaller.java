package woowa.team4.bff.api.client.delivery.caller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import woowa.team4.bff.api.client.delivery.config.DeliveryClientConfiguration;
import woowa.team4.bff.api.client.delivery.request.DeliveryTimeRequest;
import woowa.team4.bff.api.client.delivery.response.DeliveryTimeResponse;
import woowa.tema4.bff.api.client.caller.ExternalApiCaller;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryTimeApiCaller {

    private final DeliveryClientConfiguration deliveryClientConfiguration;
    private final ExternalApiCaller externalApiCaller;

    public List<DeliveryTimeResponse> send(DeliveryTimeRequest deliveryTimeRequest) {
        return externalApiCaller.post(deliveryClientConfiguration.getUrl(),
                deliveryTimeRequest,
                new ParameterizedTypeReference<>() {
                });
    }
}
