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
    CHALLENGE_EXIST("CHALLENGE_EXIST", "Challenge đã tồn tại"),
    TOPIC_NOT_EXIST("TOPIC_NOT_EXIST", "Chủ đề không tồn tại"),
    PRESCRIPT_NOT_EXIST("PRESCRIPT_NOT_EXIST", "Prescript không tồn tại"),
    CHILD_COMMENT_NOT_EXIST("CHILD_COMMENT_NOT_EXIST", "Comment level 2 không tồn tại"),
    COMMENT_NOT_EXIST("COMMENT_NOT_EXIST", "Comment không tồn tai"),
    LANGUAGE_ERROR("LANGUAGE_ERROR", "Ngôn ngữ không tồn tại hoặc sai format"),
    TESTCASE_EXISTED("TESTCASE_EXISTED", "Test case tồn tại"),
    TESTCASE_NOT_EXISTED_OR_INVALID("TESTCASE_NOT_EXISTED_OR_INVALID", "Test case không tồn tại hoặc sai id "),
    CHALLENGE_NOT_EXISTED_OR_INVALID("CHALLENGE_NOT_EXISTED_OR_INVALID", "Challenge không tồn tại hoặc sai id ");

    private final String message;
    private final String code;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
