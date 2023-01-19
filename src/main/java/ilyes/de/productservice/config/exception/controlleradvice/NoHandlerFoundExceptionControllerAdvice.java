package ilyes.de.productservice.config.exception.controlleradvice;

import ilyes.de.productservice.config.exception.ErrorResponseTo;
import ilyes.de.productservice.config.log.dto.LogContentDTOFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Map;

import static ilyes.de.productservice.config.log.logtype.LogTypeConstants.PRODUCT_ROUTE_NOT_FOUND_WARN;

@ControllerAdvice
public class NoHandlerFoundExceptionControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(NoHandlerFoundExceptionControllerAdvice.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    ResponseEntity<ErrorResponseTo> onNoHandlerFoundException(
            HttpServletRequest httpServletRequest,
            NoHandlerFoundException e) {
        String errorSummary = "Resource not found 404!";

        ErrorResponseTo errorResponseTo = new ErrorResponseTo(
                errorSummary,
                List.of(errorSummary),
                (HttpStatus) e.getStatusCode()
        );
        var logContent = Map.of("response", Map.of(
                "body",
                errorResponseTo,
                "pathUrl",
                httpServletRequest.getRequestURI(),
                "httpMethod",
                httpServletRequest.getMethod()
        ));
        LOGGER.warn(LogContentDTOFactory.createLogContentDTOAsJsonString(logContent, PRODUCT_ROUTE_NOT_FOUND_WARN,errorSummary),e);
        return new ResponseEntity<>(
                errorResponseTo,
                errorResponseTo.getErrorHttpStatus()
        );
    }
}
