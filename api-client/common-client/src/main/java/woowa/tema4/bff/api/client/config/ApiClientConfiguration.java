package woowa.tema4.bff.api.client.config;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.SlidingWindowType;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.time.Duration;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import woowa.tema4.bff.api.client.caller.AsyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.SyncClientApiCaller;
import woowa.tema4.bff.api.client.caller.WebClientCaller;

@Configuration
public class ApiClientConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public SyncClientApiCaller syncClientApiCaller(RestTemplate restTemplate) {
        return new SyncClientApiCaller(restTemplate);
    }

    @Bean
    public Executor executor() {
        return Executors.newFixedThreadPool(10);
    }

    @Bean
    public AsyncClientApiCaller asyncClientApiCaller(RestTemplate restTemplate,
            Executor executor) {
        return new AsyncClientApiCaller(restTemplate, executor);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        // 기본 closed 에서 시작
        return CircuitBreakerRegistry.of(
                CircuitBreakerConfig.custom()
                        .slidingWindowType(SlidingWindowType.COUNT_BASED) // 성공/실패 횟수를 저장
                        .slidingWindowSize(5) // 최근 n 번의 결과에 대해, default 100
                        .failureRateThreshold(50) // 실패율 n % 발생시 open, Default 50
                        .waitDurationInOpenState(Duration.ofSeconds(10)) // open 에서 n 시간 지나면 half open 으로 상태 변경
                        .permittedNumberOfCallsInHalfOpenState(3) // half open 에서 n 번은 호출할 기회를 준다, 결과에 성공이 있다면 closed 로 변경
                        .build()
        );
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        return circuitBreakerRegistry.circuitBreaker("bff");
    }

    @Bean
    public WebClientCaller webClientCaller(WebClient webClient, CircuitBreaker circuitBreaker) {
        return new WebClientCaller(webClient, circuitBreaker);
    }
}
