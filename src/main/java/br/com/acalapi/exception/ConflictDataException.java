package br.com.acalapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ConflictDataException extends RuntimeException {

    private final String message;
    private final HttpStatus status;

    public ConflictDataException(String mensagem, HttpStatus status) {
        this.message = mensagem;
        this.status = status;
    }

}
