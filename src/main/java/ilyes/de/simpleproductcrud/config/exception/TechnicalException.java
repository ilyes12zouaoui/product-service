package ilyes.de.simpleproductcrud.config.exception;

import org.springframework.http.HttpStatus;

import java.util.List;

import static ilyes.de.simpleproductcrud.config.log.constant.LogTypeConstants.TECHNICAL_ERROR_LOG;

public class TechnicalException extends RuntimeException{

    private String errorSummary;
    private List<String> errorMessages;
    private HttpStatus errorHttpStatus;
    private String logType = TECHNICAL_ERROR_LOG;

    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus, List<String> errorMessages, String logType) {
        super(errorSummary);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
        this.logType=logType;
    }
    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus, List<String> errorMessages) {
        super(errorSummary);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
    }

    public TechnicalException(String errorSummary) {
        this(errorSummary,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus) {
        this(errorSummary, errorHttpStatus,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, Throwable cause, HttpStatus errorHttpStatus, List<String> errorMessages) {
        super(errorSummary, cause);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
    }

    public TechnicalException(String errorSummary, Throwable cause, HttpStatus errorHttpStatus, List<String> errorMessages, String logType) {
        super(errorSummary, cause);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
        this.logType = logType;
    }

    public TechnicalException(String errorSummary, Throwable cause, HttpStatus errorHttpStatus) {
        this(errorSummary,cause, errorHttpStatus,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, Throwable cause) {
        this(errorSummary,cause,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, Throwable cause, String logType) {
        this(errorSummary,cause,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary),logType);
    }


    public String getErrorSummary() {
        return errorSummary;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public HttpStatus getErrorHttpStatus() {
        return errorHttpStatus;
    }

    public String getLogType() {
        return logType;
    }
}
