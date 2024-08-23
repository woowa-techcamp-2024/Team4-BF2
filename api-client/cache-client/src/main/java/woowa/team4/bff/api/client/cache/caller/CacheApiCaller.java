package woowa.team4.bff.api.client.cache.caller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.cache.config.CacheClientConfiguration;
import woowa.team4.bff.api.client.cache.request.CacheRequest;
import woowa.team4.bff.api.client.cache.response.CacheResponse;
import woowa.tema4.bff.api.client.caller.AsyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.WebClientCaller;

@Component
@RequiredArgsConstructor
public class CacheApiCaller {

    private final CacheClientConfiguration cacheClientConfiguration;
    private final SyncClientApiCaller syncClientApiCaller;
    private final AsyncClientApiCaller asyncClientApiCaller;
    private final WebClientCaller webClientCaller;

    public List<CacheResponse> send(CacheRequest cacheRequest) {
        return syncClientApiCaller.post(cacheClientConfiguration.getUrl(),
                cacheRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public CompletableFuture<List<CacheResponse>> sendAsyncCompletableFuture(
            CacheRequest cacheRequest) {
        return asyncClientApiCaller.post(cacheClientConfiguration.getUrl(),
                cacheRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public Mono<List<CacheResponse>> sendAsyncMono(
            CacheRequest cacheRequest) {
        return webClientCaller.post(
                cacheClientConfiguration.getUrl(),
                cacheRequest,
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
