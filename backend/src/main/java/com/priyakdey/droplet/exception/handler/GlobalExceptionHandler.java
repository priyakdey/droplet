package com.priyakdey.droplet.exception.handler;

import com.priyakdey.droplet.exception.AuthException;
import com.priyakdey.droplet.exception.InternalServerException;
import com.priyakdey.droplet.exception.ProfileNotFoundException;
import com.priyakdey.droplet.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author Priyak Dey
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ProfileNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleProfileNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Profile Not Found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = {AuthException.class})
    public ResponseEntity<ErrorResponse> handleAuthException() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage("Could not authenticate user");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(value = {InternalServerException.class, Exception.class})
    public ResponseEntity<ErrorResponse> handleInternalServerException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }



}
