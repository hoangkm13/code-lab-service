package com.example.codelabsvc.controller.request.auth;


import com.example.codelabsvc.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    @NotNull
    private String token;

    private User user;
}
