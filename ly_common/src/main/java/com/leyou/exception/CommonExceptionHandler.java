package com.leyou.exception;


import com.leyou.vo.ExceptionResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CommonExceptionHandler {

   @ExceptionHandler(LyException.class)
    public ResponseEntity<ExceptionResult> handlerException(LyException e){

       return ResponseEntity.status(e.getExceptionEnum().getCode()).
                body(new ExceptionResult(e.getExceptionEnum()));
    }
}
