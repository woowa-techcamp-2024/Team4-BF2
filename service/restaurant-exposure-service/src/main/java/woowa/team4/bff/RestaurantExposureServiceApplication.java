package woowa.team4.bff;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootApplication
@EnableWebFlux
public class RestaurantExposureServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantExposureServiceApplication.class, args);
    }

}
