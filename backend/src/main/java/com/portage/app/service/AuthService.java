package com.portage.app.service;

import com.portage.app.dto.request.ChangePasswordRequest;
import com.portage.app.dto.request.LoginRequest;
import com.portage.app.dto.request.RegisterRequest;
import com.portage.app.dto.response.AuthResponse;
import com.portage.app.dto.response.UserResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse register(RegisterRequest request);
    void changePassword(Long userId, ChangePasswordRequest request);
    UserResponse getCurrentUser(String email);
}
