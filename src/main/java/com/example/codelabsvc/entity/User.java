package com.example.codelabsvc.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User {

    private String id;

    private String username;

    private String firstName;

    private String lastName;

    private String avatar;

    @NotBlank(message = "Giới tính không được để trống !")
    private String gender;

    private String birthOfDate;

    private String mobile;

    private String passwordHash;

    private String role;

    private String email;

}
