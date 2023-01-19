package ilyes.de.productservice.config.log.dto;

import static ilyes.de.productservice.config.log.logtype.LogTypeConstants.PRODUCT_FRAMEWORK;

public class LogContentDTO<T>{
    public static final String CLASS_PROPERTY_NAME_DATA = "data";
    public static final String CLASS_PROPERTY_NAME_LOG_TYPE = "logType";
    public static final String CLASS_PROPERTY_NAME_TITLE = "title";

    public T data = (T) new Object();
    public String logType = PRODUCT_FRAMEWORK;
    public String title = "";

    public LogContentDTO() {
    }

    public LogContentDTO(T data) {
        this.data = data;
    }

    public LogContentDTO(String title) {
        this.title = title;
    }

    public LogContentDTO(T data, String logType) {
        this.data = data;
        this.logType = logType;
    }

    public LogContentDTO(T data, String logType, String title) {
        this.data = data;
        this.logType = logType;
        this.title = title;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }
}
