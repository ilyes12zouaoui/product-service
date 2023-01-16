package ilyes.de.simpleproductcrud.config.exception.controlleradvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import ilyes.de.simpleproductcrud.config.exception.ErrorResponseTo;
import ilyes.de.simpleproductcrud.config.exception.TechnicalException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import static ilyes.de.simpleproductcrud.config.log.constant.LogTypeConstants.TECHNICAL_ERROR_LOG;
import static ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TechnicalExceptionControllerAdvice {
    private static final Logger LOGGER = LogManager.getLogger(TechnicalExceptionControllerAdvice.class);
    @ExceptionHandler(TechnicalException.class)
    @ResponseBody
    ResponseEntity<ErrorResponseTo> onTechnicalException(
            TechnicalException e, HttpServletRequest request) throws JsonProcessingException {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(
                e.getErrorSummary(),
                e.getErrorMessages(),
                e.getErrorHttpStatus()
        );

        LOGGER.error(createLogContentDTOAsJsonStringWithDataAndLogType(errorResponseTo, TECHNICAL_ERROR_LOG),e);

        return new ResponseEntity<>(
                errorResponseTo,
                errorResponseTo.getErrorHttpStatus()
        );
    }
}
