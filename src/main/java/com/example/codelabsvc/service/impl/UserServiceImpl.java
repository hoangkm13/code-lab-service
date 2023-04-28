package com.example.codelabsvc.service.impl;

import com.example.codelabsvc.constant.ErrorCode;
import com.example.codelabsvc.constant.Role;
import com.example.codelabsvc.controller.request.auth.ResetPasswordDTO;
import com.example.codelabsvc.controller.request.auth.UpdateUserDTO;
import com.example.codelabsvc.controller.request.auth.UserDTO;
import com.example.codelabsvc.entity.User;
import com.example.codelabsvc.exception.CustomException;
import com.example.codelabsvc.repository.UserRepository;
import com.example.codelabsvc.service.UserService;
import com.example.codelabsvc.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthUtils authUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthUtils authUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authUtils = authUtils;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        User User = optionalUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(User.getRole());
        authorities.add(authority);
        return new org.springframework.security.core.userdetails.User(User.getUsername(), User.getPasswordHash(), authorities);
    }

    @Override
    public User findByUsername(String username) throws CustomException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new CustomException(ErrorCode.User_NOT_EXIST);
        }
        return optionalUser.get();
    }

    @Override
    public User createUser(UserDTO UserDTO) throws CustomException {
        if (userRepository.existsByUsername(UserDTO.getUsername())) {
            throw new CustomException(ErrorCode.USERNAME_EXIST);
        }
        if (userRepository.existsByEmail(UserDTO.getEmail())) {
            throw new CustomException(ErrorCode.EMAIL_EXIST);
        }

        User User = new User();
        User.setId(UUID.randomUUID().toString());
        User.setGender(UserDTO.getGender());
        User.setBirthOfDate(UserDTO.getBirthOfDate());
        User.setMobile(UserDTO.getMobile());
        User.setRole(Role.USER.getValue());
        User.setUsername(UserDTO.getUsername());
        User.setEmail(UserDTO.getEmail());
        User.setFirstName(UserDTO.getFirstName());
        User.setLastName(UserDTO.getLastName());
        User.setPasswordHash(passwordEncoder.encode(UserDTO.getPassword()));
        return userRepository.save(User);
    }

    @Override
    public void checkPermission(String userId) throws CustomException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = findById(authentication.getName());
        if (!userId.equals(currentUser.getId()) && !authUtils.isAdmin()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }
    }

    @Override
    public User findById(String UserId) throws CustomException {
        var user = this.userRepository.findById(UserId);
        if (user.isEmpty()) {
            throw new CustomException(ErrorCode.User_NOT_EXIST);
        }

        return user.get();
    }

    @Override
    public User preCheckUpdateUserInfo(UpdateUserDTO updateUserDTO, String currentUserId, String UserId) throws CustomException {
        var existedUser = this.findById(currentUserId);

        if (!Objects.equals(UserId, existedUser.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        return this.updateUser(updateUserDTO, existedUser);
    }

    @Override
    public User resetPassword(ResetPasswordDTO resetPasswordDTO, String currentUserId, String userId) throws CustomException {
        var existedUser = this.findById(currentUserId);

        if (!Objects.equals(userId, existedUser.getId())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        existedUser.setPasswordHash(passwordEncoder.encode(resetPasswordDTO.getPassword()));

        return this.userRepository.save(existedUser);
    }

    private User updateUser(UpdateUserDTO updateUserDTO, User existedUser) {

        existedUser.setGender(updateUserDTO.getGender() != null ? updateUserDTO.getGender() : existedUser.getGender());
        existedUser.setBirthOfDate(updateUserDTO.getBirthOfDate() != null ? updateUserDTO.getBirthOfDate() : existedUser.getBirthOfDate());
        existedUser.setMobile(updateUserDTO.getMobile() != null ? updateUserDTO.getMobile() : existedUser.getMobile());
        existedUser.setEmail(updateUserDTO.getEmail() != null ? updateUserDTO.getEmail() : existedUser.getEmail());
        existedUser.setFirstName(updateUserDTO.getFirstName() != null ? updateUserDTO.getFirstName() : existedUser.getFirstName());
        existedUser.setLastName(updateUserDTO.getLastName() != null ? updateUserDTO.getLastName() : existedUser.getLastName());
        existedUser.setEmail(updateUserDTO.getEmail() != null ? updateUserDTO.getEmail() : existedUser.getEmail());

        this.userRepository.save(existedUser);

        return existedUser;
    }
}
