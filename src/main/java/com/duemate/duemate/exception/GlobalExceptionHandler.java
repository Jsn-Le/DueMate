package com.duemate.duemate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    private static final String BILL_NOT_FOUND = "BILL_NOT_FOUND";
    private static final String USER_ALREADY_EXISTS = "USER_ALREADY_EXISTS";

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundResponse(UserNotFoundException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(404, message, USER_NOT_FOUND);
        ResponseEntity<ErrorResponse> response = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(BillNotFoundException.class)
    public ResponseEntity<ErrorResponse> billNotFoundResponse(BillNotFoundException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(404, message, BILL_NOT_FOUND);
        ResponseEntity<ErrorResponse> response = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
        return response;
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ErrorResponse> duplicateUserResponse(DuplicateUserException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(409, message, USER_ALREADY_EXISTS);
        ResponseEntity<ErrorResponse> response = new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.CONFLICT);
        return response;
    }

}
