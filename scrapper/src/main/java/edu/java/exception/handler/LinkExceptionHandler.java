package edu.java.exception.handler;

import edu.java.exception.ErrorMessage;
import edu.java.exception.exception.LinkAlreadyTrackedException;
import edu.java.exception.exception.LinkNotFoundException;
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
            String.valueOf(HttpStatus.NOT_FOUND.value()),
            ex.getClass().getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        );
    }

    @ExceptionHandler(LinkAlreadyTrackedException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorMessage linkAlreadyTrackedExceptionHandler(LinkAlreadyTrackedException ex, WebRequest request) {
        return new ErrorMessage(
            "Link already tracked.",
            String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value()),
            ex.getClass().getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        );
    }

    @ExceptionHandler(LinkNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage linkNotFoundExceptionHandler(LinkNotFoundException ex, WebRequest request) {
        return new ErrorMessage(
            "Link not found.",
            String.valueOf(HttpStatus.NOT_FOUND.value()),
            ex.getClass().getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        );
    }
}
