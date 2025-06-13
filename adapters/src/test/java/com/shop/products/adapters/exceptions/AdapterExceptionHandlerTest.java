package com.shop.products.adapters.exceptions;

import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;

import static org.junit.jupiter.api.Assertions.*;

class AdapterExceptionHandlerTest {

    AdapterExceptionHandler adapterExceptionHandler = new AdapterExceptionHandler();

    @Test
    void shouldHandleBadRequestException_returnBadRequestResponse() {
        ResponseEntity responseEntity = adapterExceptionHandler.handleBadRequestExceptions(new MissingServletRequestParameterException("productId", "String"));
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void shouldHandleProductNotFoundException_returnNotFoundResponse() {
        ResponseEntity responseEntity = adapterExceptionHandler.handleNotFoundException(new ProductNotFoundException("Product not found"));
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
}