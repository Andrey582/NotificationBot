package edu.java.bot.filter;

import io.micrometer.core.instrument.Counter;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageCounterFilter implements Filter {

    @Autowired
    private Counter counter;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        counter.increment(1.0);
    }
}
