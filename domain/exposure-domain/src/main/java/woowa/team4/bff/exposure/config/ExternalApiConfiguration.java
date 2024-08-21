package woowa.team4.bff.exposure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConfigurationProperties(prefix = "external-api")
@Getter
@Setter
public class ExternalApiConfiguration {

    private String host;
    private AdvertisementConfig advertisementService;
    private DeliveryServiceConfig deliveryService;
    private CouponServiceConfig couponService;

    @Getter
    @Setter
    public static class AdvertisementConfig {

        private String endpoints;
    }

    @Getter
    @Setter
    public static class DeliveryServiceConfig {

        private String endpoints;
    }

    @Getter
    @Setter
    public static class CouponServiceConfig {

        private String endpoints;
    }

    public String getAdvertisementUrl() {
        return host + advertisementService.endpoints;
    }

    public String getDeliveryUrl() {
        return host + advertisementService.endpoints;
    }

    public String getCouponUrl() {
        return host + advertisementService.endpoints;
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
