package woowa.tema4.bff.api.client.caller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class WebClientCaller {

    private final WebClient webClient;
    private final CircuitBreaker circuitBreaker;


    public <T, R> Mono<R> post(String url, T requestBody,
            ParameterizedTypeReference<R> responseType) {
        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .transform(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorResume(throwable -> fallback(throwable))
                ;
    }

    private Mono fallback(Throwable t) {
        log.error("error fallback");
        return Mono.empty();
    }

    // 블로킹 방식으로 결과를 받고 싶은 경우 사용할 수 있는 메서드
    public <T, R> R postBlocking(String url, T requestBody,
            ParameterizedTypeReference<R> responseType) {
        return post(url, requestBody, responseType).block();
    }
}
