package com.rewards.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RewardsManagementExceptionHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<Object> handleCustomerNotFoundException(CustomerNotFoundException exception) {
        return new ResponseEntity<>("Invalid Customer ID", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = RewardsNotAvailableException.class)
    public ResponseEntity<Object> handleRewardsNotAvailableException(RewardsNotAvailableException exception) {
        return new ResponseEntity<>("Rewards not available for this customer", HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return new ResponseEntity<>("Internal Error Occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
