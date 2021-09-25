package com.github.alextheartisan.boox.config;

import static java.util.Map.entry;
import static java.util.Map.of;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String ERROR_MESSAGE_TEMPLATE = "Error: %s%n on uri: %s";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException exception,
        HttpHeaders headers,
        HttpStatus status,
        WebRequest request
    ) {
        var errors = exception
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList());

        var body = Map.ofEntries(
            entry("timestamp", LocalDateTime.now()),
            entry("status", status.value()),
            entry("errors", errors)
        );

        return ResponseEntity.status(status).body(body);
    }

    @ExceptionHandler({ Exception.class })
    protected ResponseEntity<Object> handleAllExceptions(Exception exception, WebRequest request) {
        var responseStatus = exception.getClass().getAnnotation(ResponseStatus.class);
        var status = responseStatus != null
            ? responseStatus.value()
            : HttpStatus.INTERNAL_SERVER_ERROR;

        var message = exception.getMessage();
        var path = request.getDescription(false);

        log.error(String.format(ERROR_MESSAGE_TEMPLATE, message, path), exception);

        return ResponseEntity.status(status).body(message);
    }
}
