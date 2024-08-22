package woowa.tema4.bff.api.client.caller;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientCaller {

    private final WebClient webClient;

    public WebClientCaller(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T, R> Mono<R> post(String url, T requestBody,
            ParameterizedTypeReference<R> responseType) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType);
    }

    // 블로킹 방식으로 결과를 받고 싶은 경우 사용할 수 있는 메서드
    public <T, R> R postBlocking(String url, T requestBody,
            ParameterizedTypeReference<R> responseType) {
        return post(url, requestBody, responseType).block();
    }
}
