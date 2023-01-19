package ilyes.de.productservice.config.log.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ilyes.de.productservice.config.exception.TechnicalException;
import ilyes.de.productservice.config.utils.objectmapper.ObjectMapperUtilsFactory;

import java.util.Map;

public class LogContentDTOFactory {

    private static final ObjectMapper objectMapperThatIgnoresNullFieldsSerialization = ObjectMapperUtilsFactory
            .createObjectMapperThatIgnoresNullFieldsSerialization();

    public static <T> String createLogContentDTOAsJsonStringWithTitle(String title) {
        LogContentDTO<Object> logContentDTO= createLogContentDTOWithTitle(title);
        try {
            return objectMapperThatIgnoresNullFieldsSerialization
                    .writeValueAsString(
                            logContentDTO
                    );
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonLogString ", e, Map.of("logContentDTO",logContentDTO,"exceptionType",JsonProcessingException.class,"exceptionMessage",e.getMessage()));
        }
    }

    public static <T> String createLogContentDTOAsJsonStringWithDataAndLogType(T data, String logType) {
        LogContentDTO<T> logContentDTO= createLogContentDTO(data,logType);
        try {
            return objectMapperThatIgnoresNullFieldsSerialization
                    .writeValueAsString(
                            logContentDTO
                    );
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonLog ", e,Map.of("logContentDTO",logContentDTO,"exceptionType",JsonProcessingException.class,"exceptionMessage",e.getMessage()));
        }
    }

    public static <T> String createLogContentDTOAsJsonString(T data, String logType, String title) {
        LogContentDTO<T> logContentDTO =  createLogContentDTO(data,logType,title);
        try {
            return objectMapperThatIgnoresNullFieldsSerialization
                    .writeValueAsString(
                            logContentDTO
                    );
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonLog ", e,Map.of("logContentDTO",logContentDTO,"exceptionType",JsonProcessingException.class,"exceptionMessage",e.getMessage()));
        }
    }

    public static <T> LogContentDTO<T> createLogContentDTO(T data) {
        return new LogContentDTO<T>(data);
    }

    public static <T> LogContentDTO<T> createLogContentDTOWithTitle(String title) {
        return new LogContentDTO<T>(title);
    }

    public static <T> LogContentDTO<T> createLogContentDTO(T data, String logType) {
        return new LogContentDTO<T>(data, logType);
    }

    public static <T> LogContentDTO<T> createLogContentDTO(T data, String logType, String title) {
        return new LogContentDTO<T>(data, logType, title);
    }

}
