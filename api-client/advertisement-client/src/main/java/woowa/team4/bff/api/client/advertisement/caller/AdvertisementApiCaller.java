package woowa.team4.bff.api.client.advertisement.caller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.advertisement.config.AdvertisementClientConfiguration;
import woowa.team4.bff.api.client.advertisement.request.AdvertisementRequest;
import woowa.team4.bff.api.client.advertisement.response.AdvertisementResponse;
import woowa.tema4.bff.api.client.caller.AsyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.WebClientCaller;

@Component
@RequiredArgsConstructor
public class AdvertisementApiCaller {

    private final AdvertisementClientConfiguration advertisementClientConfiguration;
    private final SyncClientApiCaller syncClientApiCaller;
    private final AsyncClientApiCaller asyncClientApiCaller;
    private final WebClientCaller webClientCaller;

    public List<AdvertisementResponse> send(AdvertisementRequest advertisementRequest) {
        return syncClientApiCaller.post(advertisementClientConfiguration.getUrl(),
                advertisementRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public CompletableFuture<List<AdvertisementResponse>> sendAsyncCompletableFuture(
            AdvertisementRequest advertisementRequest) {
        return asyncClientApiCaller.post(advertisementClientConfiguration.getUrl(),
                advertisementRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public Mono<List<AdvertisementResponse>> sendAsyncMono(
            AdvertisementRequest advertisementRequest) {
        return webClientCaller.post(
                advertisementClientConfiguration.getUrl(),
                advertisementRequest,
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
