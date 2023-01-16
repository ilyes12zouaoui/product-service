package ilyes.de.simpleproductcrud.config.utils.objectmapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import ilyes.de.simpleproductcrud.config.exception.TechnicalException;
import ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectMapperUtilsFactory {

    private  static final ObjectMapper objectMapperThatIgnoresNullFieldsSerialization = createObjectMapperThatIgnoresNullFieldsSerialization();
    public static ObjectMapper createObjectMapperWithConcurrentMapByDefault(){
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Map.class, new JsonDeserializer<Map<?, ?>>() {
            @Override
            public Map<?, ?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return jsonParser.getCodec()
                        .readValue(jsonParser, ConcurrentHashMap.class);
            }
        });
        objectMapper.registerModule(module);
        return objectMapper;
    }

    public static ObjectMapper createObjectMapperThatIgnoresNullFieldsSerialization(){
        return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    public static String mapToJsonStringIgnoreNullValues(Object o){
        try {
            return objectMapperThatIgnoresNullFieldsSerialization.setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonStringIgnoreNull ",e);
        }
    }

}
