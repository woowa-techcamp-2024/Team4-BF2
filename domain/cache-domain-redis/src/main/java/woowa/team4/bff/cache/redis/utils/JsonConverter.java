package woowa.team4.bff.cache.redis.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowa.team4.bff.cache.redis.exception.JsonConvertException;

@Component
@RequiredArgsConstructor
public class JsonConverter {

    private final ObjectMapper objectMapper;

    public <T> String convert(T t) {
        try {
            return objectMapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e);
        }
    }

    public <T> T convert(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e);
        }
    }

    public <T> T convert(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e);
        }
    }
}
