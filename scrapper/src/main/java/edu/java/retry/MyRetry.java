package edu.java.retry;

import java.time.Duration;
import java.util.function.Predicate;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

public class MyRetry extends Retry {

    private Duration delay;
    private Long attempt;
    private Duration step;
    private Predicate<? super Throwable> errorFilter;

    public MyRetry(Long attempt, Duration delay, Duration step) {
        this.attempt = attempt;
        this.delay = delay;
        this.step = step;
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> flux) {
        return Flux.deferContextual(contextView -> flux.contextWrite(contextView).concatMap(retrySignal -> {
            if (retrySignal.totalRetries() >= attempt) {
                return Mono.error(new RuntimeException("attempt are over"));
            } else if (!errorFilter.test(retrySignal.failure())) {
                return Mono.error(new RuntimeException(retrySignal.failure()));
            }

            if (retrySignal.totalRetries() == 1) {
                delay = delay.plus(step);
            }

            return Mono.delay(delay);
        }).onErrorStop());
    }

    public MyRetry filter(Predicate<? super Throwable> errorFilter) {
        this.errorFilter = errorFilter;
        return this;
    }
}
