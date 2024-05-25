package com.web10.music.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@Slf4j
public class DemoErrorController implements ErrorController {

    @Autowired
    ErrorAttributes errorAttributes;
//
    //由用户实现，自定义错误响应时的数据结构
    protected Map<String, Object> getErrorResponseBody(HttpServletRequest request,Map<String, Object> errorAttributes,Throwable exception){

        log.error("getErrorResponseBody");
        return errorAttributes;
    }

    private ErrorAttributeOptions getErrorAttributeOptions() {
        //如果自定义，则返回如下
        ErrorAttributeOptions eao = ErrorAttributeOptions.of(
                ErrorAttributeOptions.Include.MESSAGE);//
        return eao;
    }

    @RequestMapping({"${server.error.path:${error.path:/error}}"})
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity(status);
        } else {

            WebRequest webRequest = new ServletWebRequest(request);
            Throwable excetption = (Throwable) webRequest.getAttribute("javax.servlet.error.exception", 0);

            Map<String, Object> errorMap = errorAttributes.getErrorAttributes(webRequest, getErrorAttributeOptions());

            return new ResponseEntity(errorMap, status);
        }
    }
//
    //需要的话也可以自定义，设计尽量向BasicErrorController靠拢降低学习成本
    protected HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}