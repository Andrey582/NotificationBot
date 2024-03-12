package edu.java.exception.handler;

import edu.java.exception.ErrorMessage;
import edu.java.exception.exception.UserAlreadyRegisteredException;
import edu.java.exception.exception.UserNotRegisteredException;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage userAlreadyRegisteredExceptionHandler(UserAlreadyRegisteredException ex, WebRequest request) {
        return new ErrorMessage(
            "Can`t register user.",
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            ex.getClass().getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        );
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage userNotRegisteredExceptionHandler(UserNotRegisteredException ex, WebRequest request) {
        return new ErrorMessage(
            "Can`t delete user.",
            String.valueOf(HttpStatus.BAD_REQUEST.value()),
            ex.getClass().getName(),
            ex.getMessage(),
            Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        );
    }
}
