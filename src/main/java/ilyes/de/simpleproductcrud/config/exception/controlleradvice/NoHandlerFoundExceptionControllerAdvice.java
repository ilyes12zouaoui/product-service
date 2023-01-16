package ilyes.de.simpleproductcrud.config.exception.controlleradvice;

import ilyes.de.simpleproductcrud.config.exception.ErrorResponseTo;
import ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;

import static ilyes.de.simpleproductcrud.config.log.constant.LogTypeConstants.BAD_REQUEST_ROUTE_NOT_FOUND_WARN_LOG;

@ControllerAdvice
public class NoHandlerFoundExceptionControllerAdvice {

    private static final Logger LOGGER = LogManager.getLogger(NoHandlerFoundExceptionControllerAdvice.class);

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    ResponseEntity<ErrorResponseTo> onNoHandlerFoundException(
            NoHandlerFoundException e) {
        String errorSummary = "Resource not found 404!";

        ErrorResponseTo errorResponseTo = new ErrorResponseTo(
                errorSummary,
                List.of(errorSummary),
                (HttpStatus) e.getStatusCode()
        );
        LOGGER.warn(LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType(errorResponseTo,BAD_REQUEST_ROUTE_NOT_FOUND_WARN_LOG),e);
        return new ResponseEntity<>(
                errorResponseTo,
                errorResponseTo.getErrorHttpStatus()
        );
    }
}
