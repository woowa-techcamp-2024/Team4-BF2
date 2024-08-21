package woowa.tema4.bff.api.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import woowa.tema4.bff.api.client.caller.ExternalApiCaller;

@Configuration
public class ApiClientConfiguration {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ExternalApiCaller externalApiCaller(RestTemplate restTemplate) {
        return new ExternalApiCaller(restTemplate);
    }

    @Bean
    public ApiClientProperties apiClientProperties() {
        return new ApiClientProperties();
    }
}
