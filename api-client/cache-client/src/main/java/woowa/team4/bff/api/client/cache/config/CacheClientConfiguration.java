package woowa.team4.bff.api.client.cache.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import woowa.tema4.bff.api.client.config.ApiClientConfiguration;

@Getter
@Setter
@Configuration
@Import(ApiClientConfiguration.class)
public class CacheClientConfiguration {

    @Value("${external-api.cache-service.endpoints}")
    private String endpoints;

    public String getUrl() {
        return endpoints;
    }
}
