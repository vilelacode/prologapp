package com.projectapp.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import com.projectapp.config.CustomExceptions.*;

import static com.projectapp.util.Constants.*;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(VeiculoException.class)
    public ResponseEntity<Object> handleVeiculoException(VeiculoException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ERRO_NO_VEICULO, ex);
    }

    @ExceptionHandler(PneuException.class)
    public ResponseEntity<Object> handlePneuException(PneuException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ERRO_NO_PNEU, ex);
    }

    @ExceptionHandler(VeiculoPneuException.class)
    public ResponseEntity<Object> handleVeiculoPneuException(VeiculoPneuException ex) {
        return buildResponseEntity(HttpStatus.BAD_REQUEST, ERRO_NO_VINCULO, ex);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        return buildResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, ERRO_INTERNO, ex);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String error, Exception ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", error);
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, status);
    }
}
