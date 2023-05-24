package com.example.codelabsvc.controller;

import com.example.codelabsvc.controller.request.auth.*;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.model.ApiResponse;
import com.example.codelabsvc.service.UserService;
import com.example.codelabsvc.util.TokenUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("v1/auth")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Autowired
    public UserController(AuthenticationManager authenticationManager, TokenUtils tokenUtils, UserService userService, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping(value = "/login", produces = "application/json")
    public ApiResponse<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) throws CustomException {
        var user = userService.findByUsername(loginRequestDTO.getUsername());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequestDTO.getUsername(),
                loginRequestDTO.getPassword())
        );

        String token = tokenUtils.generateToken(authentication);
        LoginResponseDTO userToken = new LoginResponseDTO(token, user);
        return ApiResponse.successWithResult(userToken);
    }

    @GetMapping(produces = "application/json")
    public ApiResponse<UserDTO> getCurrentUser() throws CustomException {
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        User User = userService.findByUsername(authentication.getUsername());
        return ApiResponse.successWithResult(modelMapper.map(User, UserDTO.class));
    }

    @PostMapping(value = "/register", produces = "application/json")
    public ApiResponse<UserDTO> register(@Valid @RequestBody UserDTO UserDTO) throws CustomException {
        var User = userService.createUser(UserDTO);
        return ApiResponse.successWithResult(modelMapper.map(User, UserDTO.class));
    }

    @PutMapping(value = "{UserId}", produces = "application/json")
    public ApiResponse<UserDTO> updateUser(@PathVariable String UserId, @Valid @RequestBody UpdateUserDTO UserDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var User = userService.preCheckUpdateUserInfo(UserDTO, authentication.getPrincipal().toString(), UserId);
        return ApiResponse.successWithResult(modelMapper.map(User, UserDTO.class));
    }

    @PostMapping(value = "changePassword/{UserId}", produces = "application/json")
    public ApiResponse<UserDTO> resetPassword(@PathVariable String UserId, @Valid @RequestBody ResetPasswordDTO resetPasswordDTO) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var User = userService.resetPassword(resetPasswordDTO, String.valueOf(authentication.getPrincipal().toString()), UserId);
        return ApiResponse.successWithResult(modelMapper.map(User, UserDTO.class));
    }
}
