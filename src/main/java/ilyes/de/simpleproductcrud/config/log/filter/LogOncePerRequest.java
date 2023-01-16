package ilyes.de.simpleproductcrud.config.log.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class LogOncePerRequest extends OncePerRequestFilter {
    private final Logger LOGGER = LogManager.getLogger(LogOncePerRequest.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString();
        String correlationId = request.getHeader("X-Correlation-ID");
        if (correlationId == null) {
            correlationId = requestId;
        }
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        boolean NoExceptionOccurred = true;

        logProcessRequestStarted(requestId, correlationId);

        filterChain.doFilter(request, response);

        stopWatch.stop();

        if (HttpStatus.valueOf(response.getStatus()).isError()) {
            ThreadContext.clearAll();
        } else {
            long requestTimeMilliseconds = stopWatch.getTotalTimeNanos();
            logProcessRequestFinished(requestTimeMilliseconds, requestId);
        }

    }

    private void logProcessRequestStarted(String requestId, String correlationId) {
        // wol ba3d log mte3 api call
        ThreadContext.put("correlationId", correlationId);
        ThreadContext.put("requestId", requestId);
        ThreadContext.put("requestBusinessContext", correlationId);
        //todo: exception add logtype parameter
        //todo: request url, header, body, query, path inside data
        //todo: how to know businessObjectif at this level ?
        LOGGER.info("correlationId {}", correlationId);

        //   LOGGER.info("requestId {}, host {}  HttpMethod: {}, URI : {}",correlationId, request.getHeader("host"),
        //           request.getMethod(), request.getRequestURI() );request.getAttribute("requestId")

    }

    private void logProcessRequestFinished(long requestTimeNano, String correlationId) {
        ThreadContext.put("responseTime", String.format("%d", requestTimeNano));
        ThreadContext.put("responseTimeSeconds", String.format("%.3f", (float) requestTimeNano / 1_000_000_000));
        // todo log response body, status, header inside data
        LOGGER.info("requestId {},request take time: {}", correlationId, 555);
        ThreadContext.clearAll();

    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }
}
