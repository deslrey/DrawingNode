package org.deslre.common.exception;

import org.deslre.common.result.Results;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Results<String> error(Exception e) {
        e.printStackTrace();
        return Results.fail();
    }

    @ResponseBody
    @ExceptionHandler(DeslreException.class)
    public Results<String> error(DeslreException e) {
        e.printStackTrace();
        return Results.fail(e.getResultCodeEnum());
    }
}
