package ilyes.de.productservice.config.exception.controlleradvice;

import ilyes.de.productservice.config.exception.ErrorResponseTo;
import ilyes.de.productservice.config.exception.TechnicalException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static ilyes.de.productservice.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonString;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TechnicalExceptionControllerAdvice {
    private static final Logger LOGGER = LogManager.getLogger(TechnicalExceptionControllerAdvice.class);
    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    ResponseEntity<ErrorResponseTo> onTechnicalException(
            HttpServletRequest httpServletRequest,
            TechnicalException e) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(
                e.getErrorSummary(),
                e.getErrorMessages(),
                e.getErrorHttpStatus()
        );
        var logContent = Map.of(
                "logData",e.getLogData(),

                "response", Map.of(
                "body",
                errorResponseTo,
                "pathUrl",
                httpServletRequest.getRequestURI(),
                "httpMethod",
                httpServletRequest.getMethod()
        ));
        if(e.getErrorHttpStatus().is4xxClientError()){
            LOGGER.warn(createLogContentDTOAsJsonString(logContent, e.getLogType(),errorResponseTo.getErrorSummary()),e);
        }else {
            LOGGER.error(createLogContentDTOAsJsonString(logContent, e.getLogType(),errorResponseTo.getErrorSummary()),e);
        }


        return new ResponseEntity<>(
                errorResponseTo,
                errorResponseTo.getErrorHttpStatus()
        );
    }
}
