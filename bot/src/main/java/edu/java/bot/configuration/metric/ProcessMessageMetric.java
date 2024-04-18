package edu.java.bot.configuration.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessMessageMetric {

    @Bean
    public CompositeMeterRegistry registry() {
        return new CompositeMeterRegistry();
    }

    @Bean
    public Counter counter(MeterRegistry registry) {
        return registry.counter("message counter", "message", "processed");
    }
}
