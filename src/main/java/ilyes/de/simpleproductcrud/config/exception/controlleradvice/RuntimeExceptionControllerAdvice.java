package ilyes.de.simpleproductcrud.config.exception.controlleradvice;

import ilyes.de.simpleproductcrud.config.exception.ErrorResponseTo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static ilyes.de.simpleproductcrud.config.log.constant.LogTypeConstants.RUNTIME_ERROR_LOG;
import static ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType;

@ControllerAdvice
public class RuntimeExceptionControllerAdvice {
    private static final Logger LOGGER = LogManager.getLogger(RuntimeExceptionControllerAdvice.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    ResponseEntity<ErrorResponseTo> onRuntimeException(
            RuntimeException e) {
        String errorSummary = "Internal Server error!";

        ErrorResponseTo errorResponseTo = new ErrorResponseTo(
                errorSummary,
                List.of(errorSummary),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        LOGGER.error(createLogContentDTOAsJsonStringWithDataAndLogType(errorResponseTo, RUNTIME_ERROR_LOG),e);

        return new ResponseEntity<>(
                errorResponseTo,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
