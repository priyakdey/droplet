package com.priyakdey.droplet.exception;

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
        ErrorResponse errorResponse = new ErrorResponse("Profile Not Found",
                "The requested profile does not exist");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(value = {AuthException.class})
    public ResponseEntity<ErrorResponse> handleAuthException() {
        ErrorResponse errorResponse = new ErrorResponse("Unauthorized",
                "Authentication failed. Please check your credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

    @ExceptionHandler(value = {InternalServerException.class, Exception.class})
    public ResponseEntity<ErrorResponse> handleInternalServerException() {
        ErrorResponse errorResponse = new ErrorResponse("Oops Something Went Wrong!",
                "An unexpected error occured please try again");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
