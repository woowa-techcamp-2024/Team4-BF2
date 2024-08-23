package woowa.tream4.bff.api.client.config;

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
public class SearchClientConfiguration {

    @Value("${external-api.search-service.endpoints}")
    private String endpoints;

    public String getUrl() {
        return endpoints;
    }
}
