package com.github.vole.common.bean.handler;


import com.github.vole.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 全局的的异常拦截器
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public R exception(Exception e) {
        log.info("保存全局异常信息 ex={}", e.getMessage(), e);
        return new R<>(e);
    }
}
