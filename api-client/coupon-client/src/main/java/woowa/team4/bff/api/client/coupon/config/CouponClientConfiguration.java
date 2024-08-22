package woowa.team4.bff.api.client.coupon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import woowa.tema4.bff.api.client.config.ApiClientConfiguration;
import woowa.tema4.bff.api.client.config.ApiClientProperties;

@Getter
@Setter
@Configuration
@Import(ApiClientConfiguration.class)
public class CouponClientConfiguration {

    @Autowired
    private ApiClientProperties apiClientProperties;
    @Value("${external-api.coupon-service.endpoints}")
    private String endpoints;

    public String getUrl() {
        return apiClientProperties.getHost() + endpoints;
    }
}
