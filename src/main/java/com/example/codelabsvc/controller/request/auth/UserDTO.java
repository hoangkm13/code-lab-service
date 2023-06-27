package com.example.codelabsvc.controller.request.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String id;

    @NotBlank(message = "Tên tài khoản không được để trống !")
    @Length(max = 20, min = 6)
    private String username;

    @NotBlank(message = "Giới tính không được để trống !")
    private String gender;

    @NotBlank(message = "Ngày sinh không được để trống !")
    private String birthOfDate;

    @NotBlank(message = "Tên không được để trống")
    private String firstName;

    @NotBlank(message = "Họ không được để trống")
    private String lastName;

    @NotBlank(message = "Số điện thoại không được để trống !")
    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String mobile;

    @NotNull
    @Length(max = 20, min = 6)
    private String password;

    private String country;

    private String github;

    private String facebook;

    private String avatar;

    private String role;

    @NotBlank(message = "Mail không được để trống !")
    @Email(message = "Sai định dạng email !")
    @Size(max = 256, message = "Mail Tối đa 256 ký tự !")
    private String email;
}
