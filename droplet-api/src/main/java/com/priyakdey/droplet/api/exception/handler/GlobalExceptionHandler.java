package com.priyakdey.droplet.api.exception.handler;

import com.priyakdey.droplet.api.exception.InvalidInputException;
import com.priyakdey.droplet.api.model.response.v1.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Priyak Dey
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({InvalidInputException.class})
    public ResponseEntity<ErrorResponse> handleInvalidInputException(Exception ex) {
        log.error(ex.getMessage());
        ErrorResponse response = new ErrorResponse(BAD_REQUEST.value(), ex.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

}
