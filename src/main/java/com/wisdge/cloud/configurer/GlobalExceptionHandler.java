package com.wisdge.cloud.configurer;

import com.wisdge.cloud.dto.ApiResult;
import com.wisdge.cloud.dto.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.auth.AuthenticationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.nio.file.AccessDeniedException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    protected HttpServletResponse response;

    /**
     * 参数未通过验证异常
     * @param exception MethodArgumentNotValidException
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ApiResult MethodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        return ApiResult.build(ResultCode.BAD_REQUEST.getCode(), exception.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 无法解析参数异常
     * @param exception
     */
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Object HttpMessageNotReadableHandler(HttpMessageNotReadableException exception) throws Exception {
        response.setStatus(ResultCode.BAD_REQUEST.getCode());
        return ApiResult.fail("参数无法正常解析");
    }

    /**
     * 方法访问权限不足异常
     * @param exception
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public Object AccessDeniedExceptionHandler(AccessDeniedException exception) throws Exception {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        return ApiResult.forbidden("方法访问权限不足");
    }

    @ExceptionHandler(value = NoHandlerFoundException.class)
    public Object NoHandlerFoundExceptionHandler(NoHandlerFoundException exception) throws Exception {
        response.setStatus(ResultCode.NOT_FOUND.getCode());
        return ApiResult.notFound("链接不存在");
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public Object AuthenticationExceptionHandler(AuthenticationException e) {
        response.setStatus(ResultCode.FORBIDDEN.getCode());
        return ApiResult.forbidden();
    }

    @ExceptionHandler(value = DuplicateKeyException.class)
    public Object DuplicateKeyExceptionHandler(DuplicateKeyException e) throws Exception {
        String source = e.getMessage();
        Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key '(.*)'");
        Matcher matcher = pattern.matcher(source);
        response.setStatus(ResultCode.BAD_REQUEST.getCode());
        if (matcher.find()) {
            String field = matcher.group(2);
            return ApiResult.internalError("字段" + field + "唯一性检查失败");
        } else
            return ApiResult.internalError("数据库字段唯一性检查失败");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResult exceptionHandler(Exception e) {
        log.error(e.getMessage(), e);
        response.setStatus(ResultCode.BAD_REQUEST.getCode());
        return ApiResult.fail(e.getMessage());
    }

}
