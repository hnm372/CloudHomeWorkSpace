package com.workspace.backend.config;

import com.workspace.backend.commom.R;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R methodParamNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        return R.error().message(allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("；")));
    }

    @ExceptionHandler(value = BindException.class)
    public R methodParamNotValid(HttpServletRequest req, BindException ex) {
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        return R.error().message(allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining("；")));
    }


}
