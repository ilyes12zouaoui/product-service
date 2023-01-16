package ilyes.de.simpleproductcrud.config.log.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ilyes.de.simpleproductcrud.config.exception.TechnicalException;
import ilyes.de.simpleproductcrud.config.utils.objectmapper.ObjectMapperUtilsFactory;

public class LogContentDTOFactory {

    private static final ObjectMapper objectMapperThatIgnoresNullFieldsSerialization = ObjectMapperUtilsFactory.createObjectMapperThatIgnoresNullFieldsSerialization();

    public static <T> String createLogContentDTOAsJsonStringWithData(T data) {
        try {
            return objectMapperThatIgnoresNullFieldsSerialization
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(
                            createLogContentDTO(data)
                    );
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonLog ", e);
        }
    }

    public static <T> String createLogContentDTOAsJsonStringWithTitle(String title) {
        try {
            return objectMapperThatIgnoresNullFieldsSerialization
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(
                            createLogContentDTOWithTitle(title)
                    );
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonLog ", e);
        }
    }

    public static <T> String createLogContentDTOAsJsonStringWithDataAndLogType(T data, String logType) {
        try {
            return objectMapperThatIgnoresNullFieldsSerialization
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(
                            createLogContentDTO(data,logType)
                    );
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonLog ", e);
        }
    }

    public static <T> String createLogContentDTOAsJsonString(T data, String logType, String title) {
        try {
            return objectMapperThatIgnoresNullFieldsSerialization
                    .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                    .writeValueAsString(
                            createLogContentDTO(data,logType,title)
                    );
        } catch (JsonProcessingException e) {
            throw new TechnicalException("Failed to mapToJsonLog ", e);
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
