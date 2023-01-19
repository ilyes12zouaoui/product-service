package ilyes.de.productservice.config.aop.aspect;

import ilyes.de.productservice.config.aop.annotation.WithMethodLogs;
import ilyes.de.productservice.config.exception.TechnicalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

import static ilyes.de.productservice.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType;
import static ilyes.de.productservice.config.log.logtype.LogTypeConstants.PRODUCT_TECHNICAL_ERROR;

@Aspect
@Component
public class AspectTwo {
    private static final Logger LOGGER = LogManager.getLogger(AspectTwo.class);

    @Around(value = "@annotation(withResourceLogs)")
    public Object around(ProceedingJoinPoint joinPoint, WithMethodLogs withResourceLogs) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] sigParamNames = methodSignature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        String logType = "PRODUCT_MAPPER";
        Map<String, Object> mapperVariables = new HashMap<>();
        for (int i = 0; i < paramValues.length; i++) {
            String paramName = sigParamNames[i];
            if (paramName.equals("logType")) {
                logType = paramValues[i].toString();
            } else {
                mapperVariables.put(paramName, paramValues[i]);
            }
        }
        String logTypeRequest = String.format("%s_BEGIN", logType);
        String logTypeResponse = String.format("%s_END", logType);
        String logTypeWarn = String.format("%s_WARN", logType);
        String logTypeError = String.format("%s_ERROR", logType);
        Map<String, Object> logData = Map.of(
                "request",
                Map.of(
                        "mapperVariables",
                        mapperVariables
                )
        );

        LOGGER.info(createLogContentDTOAsJsonStringWithDataAndLogType(logData, logTypeRequest));
        try {
            Object responseBody = joinPoint.proceed();
            stopWatch.stop();
            long requestTimeNano = stopWatch.getTotalTimeNanos();
            Map<String, Object> logResponse = new HashMap<>();
            logResponse.put("response", responseBody);
            logResponse.put("executionTimeNano", String.format("%d", requestTimeNano));
            logResponse.put("executionTimeSeconds", String.format("%.4f", (float) requestTimeNano / 1_000_000_000));
            LOGGER.info(createLogContentDTOAsJsonStringWithDataAndLogType(logResponse, logTypeResponse));
            return responseBody;
        } catch (TechnicalException ex) {
            if (ex.getLogType().equals(PRODUCT_TECHNICAL_ERROR)) {
                if (ex.getErrorHttpStatus().is4xxClientError()) {
                    ex.setLogType(logTypeWarn);
                } else {
                    ex.setLogType(logTypeError);
                }
            }
            throw ex;
        } catch (NullPointerException ex) {
            throw new TechnicalException("internal server error!", ex, logTypeError, Map.of("exceptionType", ex.getClass()));
        } catch (RuntimeException ex) {
            throw new TechnicalException("internal server error!", ex, logTypeError, Map.of("exceptionType", ex.getClass(), "errorMessage", ex.getMessage()));
        } finally {
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
        }

    }
}
