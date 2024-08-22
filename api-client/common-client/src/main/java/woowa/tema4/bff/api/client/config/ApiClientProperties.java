package woowa.tema4.bff.api.client.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class ApiClientProperties {

    @Value("${external-api.host}")
    private String host;
}
