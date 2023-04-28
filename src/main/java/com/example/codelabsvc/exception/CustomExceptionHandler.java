package com.example.codelabsvc.exception;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.model.ApiResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Log logger = LogFactory.getLog(CustomExceptionHandler.class);

    @ExceptionHandler(value = CustomException.class)
    public ApiResponse<String> handleError(HttpServletRequest req, CustomException customException) {
        logger.error("Request: " + req.getRequestURL() + " raised " + customException);
        return ApiResponse.failureWithCode(customException.getErrorCode(), customException.getMessage());
    }

    @ExceptionHandler(value = ApiException.class)
    public ApiResponse<String> handleError(HttpServletRequest req, ApiException apiException) {
        logger.error("Request: " + req.getRequestURL() + " raised " + apiException);
        return ApiResponse.failureWithCode(apiException.getHttpStatus().toString(), apiException.getMessage(), null, apiException.getHttpStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ApiResponse<String> handleError(HttpServletRequest req, Exception exception) {
        logger.error("Request: " + req.getRequestURL() + " raised " + exception);
        return ApiResponse.failureWithCode(ErrorCode.UNKNOWN_ERROR.toString(), exception.getMessage());
    }

}
