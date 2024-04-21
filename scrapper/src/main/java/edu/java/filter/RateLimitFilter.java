package edu.java.filter;

import com.github.benmanes.caffeine.cache.Cache;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@SuppressWarnings("MagicNumber")
@Component
public class RateLimitFilter implements Filter {

    @Autowired
    private Cache<String, Bucket> caffeine;

    @Override
    public void doFilter(
        ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain filterChain
    ) throws IOException, ServletException {

        String ip = servletRequest.getRemoteAddr();

        Bucket bucket = caffeine.get(ip, i -> Bucket.builder()
                .addLimit(Bandwidth.classic(10, Refill.intervally(10, Duration.ofSeconds(10))))
                .build()
        );

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            httpServletResponse.sendError(HttpStatus.TOO_MANY_REQUESTS.value());
        }

    }
}
