package com.portage.app.service;

import com.portage.app.dto.request.UserRequest;
import com.portage.app.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse findById(Long id);
    UserResponse findByEmail(String email);
    List<UserResponse> findAll();
    UserResponse update(Long id, UserRequest request);
    void delete(Long id);
}
