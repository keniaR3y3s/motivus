package com.revoktek.motivus.controllers;

import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.exceptions.NotFoundException;
import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.Map;

@ControllerAdvice
@AllArgsConstructor
@RestControllerAdvice
public class ExceptionController {

    private final MessageProvider messageProvider;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, String>> runtimeException(BusinessException ex) {
        return ResponseEntity.badRequest().body(getMessage(ex, null));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Map<String, String>> handleInternalServerException(InternalServerException ex) {
        return ResponseEntity.internalServerError().body(getMessage(null, messageProvider.getUnexpectedError()));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, String>> runtimeNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getMessage(ex, null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleOtherExceptions(Exception ex) {
        return ResponseEntity.internalServerError().body(getMessage(null, messageProvider.getUnhandledError()));
    }

    private Map<String, String> getMessage(Exception ex, String message) {
        return Collections.singletonMap("message", ex == null ? message : ex.getMessage());
    }


}
