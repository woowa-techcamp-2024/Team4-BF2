package woowa.tema4.bff.api.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import woowa.tema4.bff.api.client.caller.ExternalApiCaller;
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
    public ExternalApiCaller externalApiCaller(RestTemplate restTemplate) {
        return new ExternalApiCaller(restTemplate);
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
