package woowa.tema4.bff.api.client.caller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
public class AsyncClientApiCaller {

    private final RestTemplate restTemplate;
    private final Executor executor;

    public <T, R> CompletableFuture<R> post(String url, T requestBody,
            ParameterizedTypeReference<R> responseType) {
        return CompletableFuture.supplyAsync(() -> {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            HttpEntity<T> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<R> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    responseType
            );
            return response.getBody();
        }, executor);
    }
}
