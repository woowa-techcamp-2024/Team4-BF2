package woowa.team4.bff.api.client.delivery.caller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.delivery.config.DeliveryClientConfiguration;
import woowa.team4.bff.api.client.delivery.request.DeliveryTimeRequest;
import woowa.team4.bff.api.client.delivery.response.DeliveryTimeResponse;
import woowa.tema4.bff.api.client.caller.AsyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.WebClientCaller;

@Slf4j
@Component
@RequiredArgsConstructor
public class DeliveryTimeApiCaller {

    private final DeliveryClientConfiguration deliveryClientConfiguration;
    private final SyncClientApiCaller syncClientApiCaller;
    private final AsyncClientApiCaller asyncClientApiCaller;
    private final WebClientCaller webClientCaller;

    public List<DeliveryTimeResponse> sendSync(DeliveryTimeRequest deliveryTimeRequest) {
        return syncClientApiCaller.post(deliveryClientConfiguration.getUrl(),
                deliveryTimeRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public CompletableFuture<List<DeliveryTimeResponse>> sendAsyncCompletableFuture(
            DeliveryTimeRequest deliveryTimeRequest) {
        return asyncClientApiCaller.post(deliveryClientConfiguration.getUrl(),
                deliveryTimeRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public Mono<List<DeliveryTimeResponse>> sendAsyncMono(DeliveryTimeRequest deliveryTimeRequest) {
        return webClientCaller.post(
                deliveryClientConfiguration.getUrl(),
                deliveryTimeRequest,
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
