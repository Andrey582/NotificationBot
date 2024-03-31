package edu.java.bot.retry;

import java.time.Duration;
import java.util.List;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

public class RetryProvider {

    public static Retry constantRetry(Long attempt, Duration delay, List<Integer> statusCode) {
        return Retry.fixedDelay(
            attempt,
            delay
        ).filter(throwable -> RetryProvider.isCorrectErrorCodeForRetry(throwable, statusCode));
    }

    public static Retry exponentialRetry(Long attempt, Duration delay, List<Integer> statusCode) {
        return Retry.backoff(
                attempt,
                delay
            ).jitter(1.0)
            .filter(throwable -> RetryProvider.isCorrectErrorCodeForRetry(throwable, statusCode));
    }

    public static Retry linearRetry(Long attempt, Duration delay, Duration step, List<Integer> statusCode) {
        return new MyRetry(
            attempt,
            delay,
            step
        ).filter(throwable -> RetryProvider.isCorrectErrorCodeForRetry(throwable, statusCode));
    }

    private static boolean isCorrectErrorCodeForRetry(Throwable throwable, List<Integer> statusCode) {
        return statusCode
            .stream()
            .anyMatch(e -> e.equals(((WebClientResponseException) throwable).getStatusCode().value()));
    }

    private RetryProvider() {
    }
}
