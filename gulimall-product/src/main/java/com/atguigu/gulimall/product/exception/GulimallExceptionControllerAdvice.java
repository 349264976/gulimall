package com.atguigu.gulimall.product.exception;

import com.atguigu.common.exception.BizCodeEnume;
import com.atguigu.common.utils.R;
import com.mysql.cj.log.Log;
import java.util.HashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice(basePackages ="com.atguigu.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleVaildException(MethodArgumentNotValidException e){
        log.error("数据校验出现问题{},异常类型{}"
                ,e.getMessage(),e.getClass());

        BindingResult bindingResult = e.getBindingResult();
        HashMap errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return R.error(400,"数据校验出现问题").put("data",errorMap);
    }
    @ExceptionHandler(value = Throwable.class)
    public R handleVaildException(Throwable e){
        log.error("数据校验出现问题{},异常类型{}"
                ,e.getMessage(),e.getClass());
        return R.error(BizCodeEnume.UNKNOWN_EXCEPTION.getCode(),BizCodeEnume.UNKNOWN_EXCEPTION.getMsg());
    }
}
