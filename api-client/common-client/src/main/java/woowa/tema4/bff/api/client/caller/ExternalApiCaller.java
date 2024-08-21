package woowa.tema4.bff.api.client.caller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class ExternalApiCaller {

    private final RestTemplate restTemplate;

    public <T, R> R post(String url, T requestBody, ParameterizedTypeReference<R> responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<T> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<R> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                request,
                responseType
        );
        return response.getBody();
    }
}
