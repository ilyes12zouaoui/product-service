package ilyes.de.simpleproductcrud.config.aop.aspect;

import ilyes.de.simpleproductcrud.config.aop.annotation.LogResource;
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
import java.util.*;
import java.util.stream.Collectors;

import static ilyes.de.simpleproductcrud.config.log.dto.LogContentDTOFactory.createLogContentDTOAsJsonStringWithDataAndLogType;

@Aspect
@Component
public class AspectOne {
    private static final Logger LOGGER = LogManager.getLogger(AspectOne.class);

    @Around(value = "@annotation(logResource)")
    public Object around(ProceedingJoinPoint joinPoint, LogResource logResource) throws Throwable {
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpServletRequest = ra.getRequest();
        HttpServletResponse httpServletResponse = ra.getResponse();
        String logTypeRequest = logResource.logTypeRequest();
        String logTypeResponse = logResource.logTypeResponse();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String[] sigParamNames = methodSignature.getParameterNames();
        Object[] paramValues = joinPoint.getArgs();
        Map<String, Object> requestVariables = new HashMap<>();
        for (int i = 0; i < paramValues.length; i++) {
            String paramName = sigParamNames[i];
            if (!paramName.equals("httpServletRequest")) {
                requestVariables.put(paramName, paramValues[i]);
            }
        }

        Map<String, Serializable> headers = Collections.list(httpServletRequest.getHeaderNames()).stream().collect(Collectors.toMap(h -> h, h -> {
            ArrayList<String> headerValues = Collections.list(httpServletRequest.getHeaders(h));
            return headerValues.size() == 1 ? headerValues.get(0) : headerValues;
        }));

        Map<String, Object> logData = Map.of("request",
                Map.of(
                        "requestVariables",
                        requestVariables,
                        "queryParams",
                        httpServletRequest.getParameterMap(),
                        "headers",
                        headers,
                        "pathUrl",
                        httpServletRequest.getRequestURI(),
                        "httpMethod",
                        httpServletRequest.getMethod()
                )
        );

        LOGGER.info(createLogContentDTOAsJsonStringWithDataAndLogType(logData, logTypeRequest));
        try {
            Object responseBody = joinPoint.proceed();
            stopWatch.stop();
            long requestTimeNano = stopWatch.getTotalTimeNanos();

            Map<String, Object> logResponse = Map.of("response",
                    Map.of(
                            "body",
                            responseBody,
                            "timeNano",
                            String.format("%d", requestTimeNano),
                            "timeSeconds",
                            String.format("%.3f", (float) requestTimeNano / 1_000_000_000),
                            "httpStatus",
                            String.valueOf(httpServletResponse.getStatus()),
                            "httpMethod",
                            httpServletRequest.getMethod()
                    )
            );

           LOGGER.info(createLogContentDTOAsJsonStringWithDataAndLogType(logResponse, logTypeResponse));
            return responseBody;
        } finally {
            ThreadContext.clearAll();
            if (stopWatch.isRunning()) {
                stopWatch.stop();
            }
        }
    }
}
