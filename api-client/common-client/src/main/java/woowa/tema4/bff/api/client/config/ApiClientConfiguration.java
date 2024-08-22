package woowa.tema4.bff.api.client.config;

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
    public ApiClientProperties apiClientProperties() {
        return new ApiClientProperties();
    }

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
    public WebClientCaller webClientCaller(WebClient webClient) {
        return new WebClientCaller(webClient);
    }
}
