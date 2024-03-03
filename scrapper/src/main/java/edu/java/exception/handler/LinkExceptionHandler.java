package edu.java.exception.handler;

import edu.java.exception.ErrorMessage;
import edu.java.exception.exception.UserHasNoLinkException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class LinkExceptionHandler {

    @ExceptionHandler(UserHasNoLinkException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage userHasNoLinkExceptionHandler(UserHasNoLinkException ex, WebRequest request) {
        return new ErrorMessage(
            "Can`t find user links.",
            String.valueOf(HttpStatus.NOT_ACCEPTABLE.value()),
            ex.getClass().getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        );
    }
}
