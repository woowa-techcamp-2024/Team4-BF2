package woowa.tema4.bff.api.client.caller;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class WebClientCaller {

    private final WebClient webClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;


    public <T, R> Mono<R> post(String url, T requestBody,
            ParameterizedTypeReference<R> responseType) {
        // CircuitBreakerRegistry 는 주어진 이름에 CircuitBreaker 객체가 없으면 생성해 캐싱한다, 있다면 캐싱해둔 객체를 반환한다
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(getCircuitBreakerName(url));

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
        log.error("error fallback {}", t.getMessage());
        return Mono.empty();
    }

    // 블로킹 방식으로 결과를 받고 싶은 경우 사용할 수 있는 메서드
    public <T, R> R postBlocking(String url, T requestBody,
            ParameterizedTypeReference<R> responseType) {
        return post(url, requestBody, responseType).block();
    }

    private String getCircuitBreakerName(String url) {
        // URL을 기반으로 CircuitBreaker 이름 생성
        // 알파벳, 숫자 외 다른 문자를 "_" 로 대체
        // 예: "http://api.example.com/users" -> "circuit_breaker_api_example_com_users"
        return "circuit_breaker_" + url.replaceAll("[^a-zA-Z0-9]", "_");
    }
}
