package com.sorryisme.fmarket.common;

import com.sorryisme.fmarket.common.dto.ResponseDto;
import com.sorryisme.fmarket.exception.DuplicateDataException;
import com.sorryisme.fmarket.exception.NotFoundDataException;
import com.sorryisme.fmarket.exception.UpdateFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {DuplicateDataException.class})
    protected ResponseEntity<ResponseDto<Object>> handleDuplicateDataException(DuplicateDataException e) {
        log.error(e.getMessage());
        ResponseDto<Object> dto = ResponseDto.fail(HttpStatus.CONFLICT.value(), null, e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ExceptionHandler(value = {NotFoundDataException.class})
    protected ResponseEntity<ResponseDto<Object>> handleNotFoundDataException(NotFoundDataException e) {
        log.error(e.getMessage());
        ResponseDto<Object> dto = ResponseDto.fail(HttpStatus.NOT_FOUND.value(), null, e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @ExceptionHandler(value = {UpdateFailException.class})
    protected ResponseEntity<ResponseDto<Object>> handleUpdateFailException(UpdateFailException e) {
        log.error(e.getMessage());
        ResponseDto<Object> dto = ResponseDto.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), null, e.getMessage());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

}
