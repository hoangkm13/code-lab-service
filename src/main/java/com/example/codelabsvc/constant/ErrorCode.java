package com.example.codelabsvc.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNAUTHORIZED("UNAUTHORIZED", "Không được quyền truy cập"),
    UNKNOWN_ERROR("UNKNOWN_ERROR", "Lỗi không xác định"),
    USER_NOT_EXIST("USER_NOT_EXIST", "Khách hàng không tồn tại"),
    USERNAME_NOT_EXIST("USERNAME_NOT_EXIST", "Tên người dùng không tồn tại"),
    USERNAME_EXIST("USERNAME_EXIST", "Tên người dùng tồn tại"),
    EMAIL_EXIST("EMAIL_EXIST", "Email người dùng tồn tại"),
    CHALLENGE_NOT_EXIST("CHALLENGE_NOT_EXIST", "Challenge không tồn tại"),
    CHALLENGE_EXIST("CHALLENGE_EXIST", "Challenge đã tồn tại");

    private final String message;
    private final String code;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}