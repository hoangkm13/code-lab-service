package com.example.codelabsvc.exception;

import com.example.codelabsvc.constant.ErrorCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends Exception {
    private String message;
    private String errorCode;

    public CustomException(ErrorCode errorCode, String... args) {
        super(String.format(errorCode.getMessage(), args));
        this.message = errorCode.getMessage();
        this.errorCode = errorCode.getCode();
    }
}
