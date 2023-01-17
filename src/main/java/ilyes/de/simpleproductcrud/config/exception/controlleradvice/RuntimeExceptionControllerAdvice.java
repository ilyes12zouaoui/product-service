package ilyes.de.simpleproductcrud.config.exception.controlleradvice;

import ilyes.de.simpleproductcrud.config.exception.ErrorResponseTo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

import static ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonString;
import static ilyes.de.simpleproductcrud.config.log.logtype.LogTypeConstants.PRODUCT_RUNTIME_ERROR;
import static ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType;

@ControllerAdvice
public class RuntimeExceptionControllerAdvice {
    private static final Logger LOGGER = LogManager.getLogger(RuntimeExceptionControllerAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    ResponseEntity<ErrorResponseTo> onRuntimeException(
            HttpServletRequest httpServletRequest,
            RuntimeException e) {
        String errorSummary = "Internal Server error!";

        ErrorResponseTo errorResponseTo = new ErrorResponseTo(
                errorSummary,
                List.of(errorSummary),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        var logContent = Map.of("response", Map.of(
                "body",
                errorResponseTo,
                "pathUrl",
                httpServletRequest.getRequestURI(),
                "httpMethod",
                httpServletRequest.getMethod()
        ));
        LOGGER.error(createLogContentDTOAsJsonString(logContent, PRODUCT_RUNTIME_ERROR,errorSummary),e);

        return new ResponseEntity<>(
                errorResponseTo,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
