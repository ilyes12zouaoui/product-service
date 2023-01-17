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
        ThreadContext.put("correlationId", correlationId);
        ThreadContext.put("requestId", requestId);

        filterChain.doFilter(request, response);

        ThreadContext.clearAll();

    }

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }
}
