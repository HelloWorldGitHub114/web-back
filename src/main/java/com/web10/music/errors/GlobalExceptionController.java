package com.web10.music.errors;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionController {

    // 身份验证错误
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity authenticationExceptionHandler(AuthenticationException e) {
        log.error("AuthenticationException");
        log.error(e.getLocalizedMessage());

        Map<String,Object> body=new HashMap<String,Object>();
        body.put("code", HttpStatus.FORBIDDEN.value());
        body.put("msg",e.getLocalizedMessage());
        body.put("data", HttpStatus.FORBIDDEN.getReasonPhrase());
        return new ResponseEntity(body, HttpStatus.FORBIDDEN);//仅是示例，按需求定义
    }

    //权限验证错误
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity unauthorizedExceptionHandler(UnauthorizedException e) {
        log.error("unauthorizedExceptionHandler");
        log.error(e.getLocalizedMessage());

        Map<String,Object> body=new HashMap<String,Object>();
        body.put("code", HttpStatus.UNAUTHORIZED.value());
        body.put("msg",e.getLocalizedMessage());
        body.put("data", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        return new ResponseEntity(body, HttpStatus.UNAUTHORIZED);//仅是示例，按需求定义
    }


    //对应路径不存在
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity noHandlerFoundExceptionHandler(NoHandlerFoundException e) {
        log.error("noHandlerFoundExceptionHandler");
        log.error(e.getLocalizedMessage());

        Map<String,Object> body=new HashMap<String,Object>();
        body.put("code", HttpStatus.NOT_FOUND.value());
        body.put("message",e.getLocalizedMessage());
        body.put("data", HttpStatus.NOT_FOUND.getReasonPhrase());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);//仅是示例，按需求定义
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity exceptionHandler(Exception e) {
        log.error("exceptionHandler");
        log.error(e.getLocalizedMessage());
        log.error(e.getStackTrace().toString());

        Map<String,Object> body=new HashMap<String,Object>();
        body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("msg",e.getLocalizedMessage());
        body.put("data", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()+"\n"+e.getClass().getName());
        return new ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR);//仅是示例，按需求定义
    }
}
