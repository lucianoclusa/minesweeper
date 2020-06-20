package ar.com.lucianoclusa.minesweeper.application;

import ar.com.lucianoclusa.minesweeper.domain.service.UserNotValidForGameException;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.tomcat.jni.Local;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleBadRequest(RuntimeException ex, WebRequest request) {
        RestError error = new RestError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {UserNotValidForGameException.class})
    protected ResponseEntity<Object> handleInvalidUser(RuntimeException ex, WebRequest request) {
        RestError error = new RestError(LocalDateTime.now(), HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.toString(), "User not authorized to play this game");
        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    private class RestError {
        @JsonProperty
        private LocalDateTime timestamp;
        @JsonProperty
        private int status;
        @JsonProperty
        private String error;
        @JsonProperty
        private String message;

        RestError(LocalDateTime timestamp, int status, String error, String message) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
        }
    }
}