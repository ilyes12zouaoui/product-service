package ilyes.de.simpleproductcrud.config.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ilyes.de.simpleproductcrud.config.log.logtype.LogTypeConstants.PRODUCT_TECHNICAL_ERROR;

public class TechnicalException extends RuntimeException{

    private String errorSummary;
    private List<String> errorMessages;
    private HttpStatus errorHttpStatus;
    private String logType = PRODUCT_TECHNICAL_ERROR;
    private Map<String,Object> logData = new HashMap<>();

    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus,List<String> errorMessages, String logType, Map<String, Object> logData) {
        super(errorSummary);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
        this.logType = logType;
        this.logData = logData;
    }

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

    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus, List<String> errorMessages, Map<String,Object> logData) {
        super(errorSummary);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
        this.logData = logData;
    }

    public TechnicalException(String errorSummary) {
        this(errorSummary,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, Map<String,Object> logData) {
        this(errorSummary,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary),logData);
    }

    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus) {
        this(errorSummary, errorHttpStatus,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus, String logType) {
        this(errorSummary, errorHttpStatus,List.of(errorSummary),logType);
    }

    public TechnicalException(String errorSummary, HttpStatus errorHttpStatus, String logType, Map<String,Object> logData) {
        this(errorSummary, errorHttpStatus,List.of(errorSummary),logType,logData);
    }

    public TechnicalException(String errorSummary, Throwable cause, HttpStatus errorHttpStatus, List<String> errorMessages) {
        super(errorSummary, cause);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
    }

    public TechnicalException(String errorSummary, Throwable cause, HttpStatus errorHttpStatus, List<String> errorMessages, Map<String,Object> logData) {
        super(errorSummary, cause);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
        this.logData = logData;
    }

    public TechnicalException(String errorSummary, Throwable cause, HttpStatus errorHttpStatus, List<String> errorMessages, String logType) {
        super(errorSummary, cause);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
        this.logType = logType;
    }

    public TechnicalException(String errorSummary,Throwable cause, HttpStatus errorHttpStatus, List<String> errorMessages, String logType, Map<String, Object> logData) {
        super(errorSummary, cause);
        this.errorSummary = errorSummary;
        this.errorMessages = errorMessages;
        this.errorHttpStatus = errorHttpStatus;
        this.logType = logType;
        this.logData = logData;
    }

    public TechnicalException(String errorSummary, Throwable cause, HttpStatus errorHttpStatus) {
        this(errorSummary,cause, errorHttpStatus,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, Throwable cause) {
        this(errorSummary,cause,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary));
    }

    public TechnicalException(String errorSummary, Throwable cause,Map<String,Object> logData) {
        this(errorSummary,cause,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary),logData);
    }

    public TechnicalException(String errorSummary, Throwable cause, String logType) {
        this(errorSummary,cause,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary),logType);
    }

    public TechnicalException(String errorSummary, Throwable cause, String logType,Map<String,Object> logData) {
        this(errorSummary,cause,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary),logType,logData);
    }

    public  TechnicalException(String errorSummary, HttpStatus errorHttpStatus, Map<String,Object> logData) {
        this(errorSummary,HttpStatus.INTERNAL_SERVER_ERROR,List.of(errorSummary),logData);
    }


    public Map<String, Object> getLogData() {
        return logData;
    }

    public void setLogData(Map<String, Object> logData) {
        this.logData = logData;
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

    public void setErrorSummary(String errorSummary) {
        this.errorSummary = errorSummary;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setErrorHttpStatus(HttpStatus errorHttpStatus) {
        this.errorHttpStatus = errorHttpStatus;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
