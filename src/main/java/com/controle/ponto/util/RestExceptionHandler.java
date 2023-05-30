package com.controle.ponto.util;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.controle.ponto.exception.InvalidRegisterException;
import com.controle.ponto.exception.InvalidRequestException;
import com.controle.ponto.exception.NotFoundException;
import com.controle.ponto.exception.TimeAlreadyRegisteredException;

@ControllerAdvice
public class RestExceptionHandler {
	
	private final MessageSource messageSource;

    RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(TimeAlreadyRegisteredException.class)
    public ResponseEntity<String> handleIllegalArgument(TimeAlreadyRegisteredException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(InvalidRegisterException.class)
    public ResponseEntity<String> handleIllegalArgument(InvalidRegisterException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleIllegalArgument(InvalidRequestException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleIllegalArgument(NotFoundException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
}
