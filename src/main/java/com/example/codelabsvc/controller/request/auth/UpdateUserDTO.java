package com.example.codelabsvc.controller.request.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {

    @Length(max = 20, min = 6)
    private String username;

    private String firstName;

    private String gender;

    private String birthOfDate;

    private String lastName;

    @Pattern(regexp = "^0\\d{9}$", message = "Số điện thoại không hợp lệ")
    private String mobile;

    @Email(message = "Sai định dạng email !")
    @Size(max = 256, message = "Mail Tối đa 256 ký tự !")
    private String email;

    private String github;

    private String facebook;

    private String country;
}
