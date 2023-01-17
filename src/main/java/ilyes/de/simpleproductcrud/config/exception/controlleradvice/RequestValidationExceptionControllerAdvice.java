package ilyes.de.simpleproductcrud.config.exception.controlleradvice;

import ilyes.de.simpleproductcrud.config.exception.ErrorResponseTo;
import ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;

import static ilyes.de.simpleproductcrud.config.log.logtype.LogTypeConstants.PRODUCT_INPUT_VALIDATION_WARN;

@ControllerAdvice
public class RequestValidationExceptionControllerAdvice {
    private static final Logger LOGGER = LogManager.getLogger(RequestValidationExceptionControllerAdvice.class);
    private final String BAD_REQUEST_ERROR_SUMMARY = "Bad request, input validation failed!";
    private final String BAD_REQUEST_ERROR_MESSAGE_FORMAT = "Property '%s' validation failed. It %s";

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorResponseTo> onConstraintValidationException(
            ConstraintViolationException e) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(BAD_REQUEST_ERROR_SUMMARY, new ArrayList<>(), HttpStatus.BAD_REQUEST);
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            errorResponseTo.getErrorMessages().add(
                    String.format(BAD_REQUEST_ERROR_MESSAGE_FORMAT, violation.getPropertyPath().toString(), violation.getMessage())
            );
        }
        LOGGER.warn(LogContentDTOFactory.createLogContentDTOAsJsonString(errorResponseTo, PRODUCT_INPUT_VALIDATION_WARN,errorResponseTo.getErrorSummary()),e);
        return new ResponseEntity<>(
                errorResponseTo,
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorResponseTo> onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ErrorResponseTo errorResponseTo = new ErrorResponseTo(BAD_REQUEST_ERROR_SUMMARY, new ArrayList<>(), HttpStatus.BAD_REQUEST);
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorResponseTo.getErrorMessages().add(
                    String.format(BAD_REQUEST_ERROR_MESSAGE_FORMAT, fieldError.getField(), fieldError.getDefaultMessage())
            );
        }
        LOGGER.warn(LogContentDTOFactory.createLogContentDTOAsJsonString(errorResponseTo, PRODUCT_INPUT_VALIDATION_WARN,errorResponseTo.getErrorSummary()),e);
        return new ResponseEntity<>(
                errorResponseTo,
                HttpStatus.BAD_REQUEST
        );
    }
}
