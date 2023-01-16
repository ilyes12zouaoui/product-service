package ilyes.de.simpleproductcrud.config.log.logeventfactory;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ilyes.de.simpleproductcrud.config.exception.TechnicalException;
import ilyes.de.simpleproductcrud.config.log.dto.LogContentDTO;
import ilyes.de.simpleproductcrud.config.utils.objectmapper.ObjectMapperUtilsFactory;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.impl.LogEventFactory;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ObjectMessage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonStringWithTitle;

public class MaskSensitiveDataLogEventFactory implements LogEventFactory {

    private final List<String> SENSITIVE_KEYS_TO_MASK = List.of(
            "errorSummary",
            "sensitive"
    );

    private final List<String> SENSITIVE_KEYS_TO_IGNORE_MASK = List.of(
            ".error.errorSummary2.errorSummary",
            ".sensitive"
    );

    @Override
    public LogEvent createEvent(String loggerName, Marker marker, String fqcn, Level level, Message message,
                                List<Property> properties, Throwable t) {

        try {
            boolean isLogContentDTOMessage = message.getFormattedMessage().contains(LogContentDTO.CLASS_PROPERTY_NAME_LOG_TYPE)
                   && message.getFormattedMessage().contains(LogContentDTO.CLASS_PROPERTY_NAME_DATA) ;

            if(isLogContentDTOMessage){
                ObjectMapper mapper = ObjectMapperUtilsFactory.createObjectMapperWithConcurrentMapByDefault();
                ConcurrentHashMap<String, Object> map = mapper.readValue(message.getFormattedMessage(), ConcurrentHashMap.class);
                maskMapSensitiveData(map);
                message = new ObjectMessage(map);
            }else{
                ObjectMapper mapper = ObjectMapperUtilsFactory.createObjectMapperWithConcurrentMapByDefault();
                ConcurrentHashMap<String, Object> map = mapper.readValue(createLogContentDTOAsJsonStringWithTitle(message.getFormattedMessage()), ConcurrentHashMap.class);
                maskMapSensitiveData(map);
                message = new ObjectMessage(map);
            }
        } catch (JsonProcessingException | TechnicalException ignored) {
            message = new ObjectMessage(createLogContentDTOAsJsonStringWithTitle(message.getFormattedMessage()));
        }

        return new Log4jLogEvent(loggerName, marker, fqcn, level, message, properties, t);
    }

    public void maskMapSensitiveData(ConcurrentHashMap<String, Object> rootMap) {
        this.maskMapSensitiveData(rootMap, "");
    }

    public void maskMapSensitiveData(ConcurrentHashMap<String, Object> rootMap, String parent) {

        rootMap.forEach((k, v) -> {
            if (v instanceof Map<?, ?>) {
                maskMapSensitiveData((ConcurrentHashMap<String, Object>) v, parent.concat("." + k));
            } else {
                if (!SENSITIVE_KEYS_TO_IGNORE_MASK.contains(parent + "." + k) && SENSITIVE_KEYS_TO_MASK.contains(k)) {
                    rootMap.put(k, "****");
                }
            }
        });
    }

}
