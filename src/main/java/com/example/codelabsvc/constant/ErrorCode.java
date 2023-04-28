package com.example.codelabsvc.constant;

import lombok.Getter;

@Getter
public enum ErrorCode {
    APPLICATION_TYPE_NOT_EXIST ("APPLICATION_TYPE_NOT_EXIST", "Sai mã sản phẩm"),
    ERROR_TOKEN("ERROR_TOKEN", "Không lấy được token"),
    CREATE_VIRTUAL_ACCOUNT_ERROR("CREATE_VIRTUAL_ACCOUNT_ERROR", "Tạo tài khoản ảo thất bại"),
    VIRTUAL_ACCOUNT_EXIST_ERROR("VIRTUAL_ACCOUNT_EXIST_ERROR", "Khách hàng đã sở hữu tài khoản ảo"),
    VIRTUAL_ACCOUNT_NOT_EXIST("VIRTUAL_ACCOUNT_NOT_EXIST", "Tài khoản ảo không tồn tại"),
    STATUS_ERROR("STATUS_ERROR", "Trạng thái sai hoặc không xác định"),
    NOT_CURRENT_LOAN_REPAYMENT_ERROR("NOT_CURRENT_LOAN_REPAYMENT_ERROR", "Bạn chưa có lịch sử thanh toán cho khoản vay hiện tại"),
    UNAUTHORIZED("UNAUTHORIZED", "Không được quyền truy cập"),
    HTTPCLIENT_ERROR("HTTPCLIENT_ERROR", "Lỗi kết nối"),
    DATA_ERROR("DATA_ERROR", "Dữ liệu đầu vào hoặc đầu ra sai hoặc không xác định"),
    EMPLOYEE_CODE_NOT_EXIST("EMPLOYEE_CODE_NOT_EXIST", "Mã nhân viên không tồn tại"),
    DO_NOT_EXIST_VIRTUAL_ACCOUNT("DO_NOT_EXIST_VIRTUAL_ACCOUNT", "Bạn chưa có tài khoản ảo"),
    ACCOUNT_NOT_EXIST("ACCOUNT_NOT_EXIST", "Tài khoản không tồn tại"),
    LOAN_NOT_EXIST("LOAN_NOT_EXIST", "Khoản vay không tồn tại"),
    LOAN_STATUS_NOT_ACCORDANT("LOAN_STATUS_NOT_ACCORDANT", "Trạng thái khoản vay không phù hợp"),
    UNKNOWN_ERROR("UNKNOWN_ERROR", "Lỗi không xác định"),
    GET_User_INFO_ERROR("GET_User_INFO_ERROR", "Lấy thông tin UserInfo thất bại"),
    CALLBACK_TYPE_ERROR("CALLBACK_TYPE_ERROR", "Link callback không tồn tại"),
    ENCRYPT_HMAC_ERROR("ENCRYPT_HMAC_ERROR", "ENCRYPT_HMAC_ERROR"),
    ORDER_REPAYMENT_ERROR("ORDER_REPAYMENT_ERROR", "Tạo đơn thanh toán nội địa thất bại"),
    UNVERIFIED_PROVIDER("UNVERIFIED_PROVIDER", "Provider is unverified"),
    LOAN_DISBURSED_ERROR("LOAN_DISBURSED_ERROR", "Khoản vay đã giải ngân"),
    LOAN_DISBURSED_PENDING_ERROR("LOAN_DISBURSED_PENDING_ERROR", "Giao dịch đang được xử lý"),
    WRONG_APPLICATION_TYPE("WRONG_APPLICATION_TYPE", "Loại sản phẩm vay không hợp lệ"),
    REPAYMENT_TRANSACTION_DONE("REPAYMENT_TRANSACTION_DONE", "Giao dịch thanh toán đã hoàn thành"),
    NOT_FOUND_REPAYMENT_ORDER_ERROR("NOT_FOUND_REPAYMENT_ORDER_ERROR", "Không tìm thấy phiên yêu cầu thanh toán"),
    TRANSACTION_DO_NOT_EXIST("TRANSACTION_DO_NOT_EXIST", "Không xác định được phiên giao dịch, mời liên hệ với chăm sóc khách hàng"),
    TRANSACTION_ALREADY_EXIST("TRANSACTION_ALREADY_EXIST", "Phiên giao dịch đã được xử lý"),
    LOAN_ALREADY_COMPLETE("LOAN_ALREADY_COMPLETE", "Khoản vay đã hoàn thành hoặc bị hủy"),
    User_NOT_EXIST("User_NOT_EXIST", "Khách hàng không tồn tại"),
    USERNAME_NOT_EXIST("USERNAME_NOT_EXIST", "Tên người dùng không tồn tại"),
    USERNAME_EXIST("USERNAME_EXIST", "Tên người dùng tồn tại"),
    EMAIL_EXIST("EMAIL_EXIST", "Email người dùng tồn tại"),
    PROVIDER_INVALID("PROVIDER_INVALID", "Provider không tồn tại"),
    AMOUNT_INVALID("AMOUNT_INVALID", "Hạn mức không hợp lệ");

    private final String message;
    private final String code;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}