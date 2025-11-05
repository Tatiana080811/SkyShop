package org.skyshop.skyshop.controller.advice;

import org.skyshop.skyshop.exception.NoSuchProductException;
import org.skyshop.skyshop.model.search.ShopError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShopControllerAdvice {

    @ExceptionHandler(value = NoSuchProductException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<ShopError> handleNoSuchProductException(NoSuchProductException exception) {
        ShopError shopError = new ShopError("PRODUCT_NOT_FOUND", "Продукт с указанным ID не найден");
        return new ResponseEntity<>(shopError, HttpStatus.NOT_FOUND);
    }
}
