package ilyes.de.simpleproductcrud.config.aop.aspect;

import ilyes.de.simpleproductcrud.config.aop.annotation.LogResource;
import ilyes.de.simpleproductcrud.config.exception.TechnicalException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType;
import static ilyes.de.simpleproductcrud.config.log.logtype.LogTypeConstants.PRODUCT_TECHNICAL_ERROR;

@Aspect
@Component
public class AspectTwo {
    private static final Logger LOGGER = LogManager.getLogger(AspectTwo.class);

    @Around("execution(* ilyes.de.simpleproductcrud.mapper..*.*WithLogs(..)) && args(..)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

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
            Map<String, Object> logResponse = Map.of("response", responseBody);
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
        }
        catch (NullPointerException ex) {
            throw new TechnicalException("internal server error!", ex, logTypeError,Map.of("exceptionType",ex.getClass()));
        }
        catch (RuntimeException ex) {
            throw new TechnicalException("internal server error!", ex, logTypeError,Map.of("exceptionType", ex.getClass(),"errorMessage",ex.getMessage()));
        }

    }
}
