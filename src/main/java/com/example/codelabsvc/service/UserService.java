package com.example.codelabsvc.service;

import com.example.codelabsvc.controller.request.auth.ResetPasswordDTO;
import com.example.codelabsvc.controller.request.auth.UpdateUserDTO;
import com.example.codelabsvc.controller.request.auth.UserDTO;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    User findByUsername(String username) throws CustomException, IOException;
    User createUser(UserDTO UserDTO) throws CustomException;
    void checkPermission(String userId) throws CustomException;
    User findById(String UserId) throws CustomException;
    User preCheckUpdateUserInfo(UpdateUserDTO updateUserDTO, String currentUserId, String userId, MultipartFile avatarFile) throws CustomException, IOException;
    User resetPassword(ResetPasswordDTO resetPasswordDTO, String currentUserId, String userId) throws CustomException;
}
