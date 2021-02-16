package br.com.acalapi;

import br.com.acalapi.exception.ConflictDataException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class AppAdvice {

    private Map<String, Object> getBody(Exception exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", exception.getMessage());
        return body;
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<?> h1(final UsernameNotFoundException exception) {
        return new ResponseEntity<>(getBody(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<?> h3(final RuntimeException exception) {
        return new ResponseEntity<>(getBody(exception), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ConflictDataException.class)
    public ResponseEntity<?> h2(ConflictDataException exception) {
        return new ResponseEntity<>(getBody(exception), exception.getStatus());
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public ResponseEntity<?> h3(ExpiredJwtException exception) {
        return new ResponseEntity<>(getBody(exception), HttpStatus.UNAUTHORIZED);
    }


}