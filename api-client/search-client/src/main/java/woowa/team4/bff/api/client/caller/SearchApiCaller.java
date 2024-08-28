package woowa.team4.bff.api.client.caller;

import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.config.SearchClientConfiguration;
import woowa.team4.bff.api.client.request.SearchRequest;
import woowa.team4.bff.api.client.response.SearchResponse;
import woowa.tema4.bff.api.client.caller.AsyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.WebClientCaller;

@Component
@RequiredArgsConstructor
public class SearchApiCaller {

    private final SearchClientConfiguration searchClientConfiguration;
    private final SyncClientApiCaller syncClientApiCaller;
    private final AsyncClientApiCaller asyncClientApiCaller;
    private final WebClientCaller webClientCaller;

    public SearchResponse send(SearchRequest SearchRequest) {
        return syncClientApiCaller.post(searchClientConfiguration.getUrl(),
                SearchRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public CompletableFuture<SearchResponse> sendAsyncCompletableFuture(
            SearchRequest SearchRequest) {
        return asyncClientApiCaller.post(searchClientConfiguration.getUrl(),
                SearchRequest,
                new ParameterizedTypeReference<>() {
                });
    }

    public Mono<SearchResponse> sendAsyncMono(
            SearchRequest SearchRequest) {
        return webClientCaller.post(
                searchClientConfiguration.getUrl(),
                SearchRequest,
                new ParameterizedTypeReference<>() {
                }
        );
    }
}
