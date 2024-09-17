package com.devsm.nextpro.global;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE = "관리자에게 문의하세요. ";

    @ExceptionHandler(value = {
            RuntimeException.class
    })
    public ResponseEntity<ResponseDto<?>> handleRuntimeException(final RuntimeException exception) {
        final String errorMessage = exception.getMessage();
        log.error(errorMessage);
        ResponseDto<?> result = new ResponseDto<>(ResponseCode.DATABASE_ERROR,errorMessage,null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
    })
    public ResponseEntity<ResponseDto<?>> handleBadRequestException(final RuntimeException exception) {
        final String errorMessage = exception.getMessage();
        log.warn(errorMessage);
        ResponseDto<?> result = new ResponseDto<>(ResponseCode.BAD_REQUEST,errorMessage,null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(result);
    }

    @ExceptionHandler(value = {
            EntityNotFoundException.class
    })
    public ResponseEntity<ResponseDto<?>> handleNotFoundException(final RuntimeException exception) {
        final String errorMessage = exception.getMessage();
        log.warn(errorMessage);
        ResponseDto<?> result = new ResponseDto<>(ResponseCode.NOT_EXISTED,errorMessage,null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(result);
    }
}
