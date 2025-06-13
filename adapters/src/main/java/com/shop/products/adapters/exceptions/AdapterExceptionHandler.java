package com.shop.products.adapters.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class AdapterExceptionHandler {

    private static final String ERROR_INVALID_PARAMETERS = "There was an exception validating input parameters: {}";

    @ExceptionHandler({
        ConstraintViolationException.class,
        BindException.class,
        HttpMessageNotReadableException.class,
        MethodArgumentNotValidException.class,
        MissingServletRequestParameterException.class
    })
    public ResponseEntity handleBadRequestExceptions(Exception ex) {
        log.error(ERROR_INVALID_PARAMETERS, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("data", ERROR_INVALID_PARAMETERS));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity handleNotFoundException(ProductNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.notFound().build();
    }
}
