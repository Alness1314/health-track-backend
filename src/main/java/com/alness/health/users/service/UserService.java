package com.alness.health.users.service;

import java.util.List;
import java.util.Map;

import com.alness.health.common.dto.ResponseDto;
import com.alness.health.users.dto.request.UserRequest;
import com.alness.health.users.dto.response.UserResponse;

public interface UserService {
    public UserResponse save(UserRequest request);
    public UserResponse findOne(String id);
    public UserResponse findByUsername(String username);
    public List<UserResponse> find(Map<String, String> params);
    public UserResponse update(String id, UserRequest request);
    public ResponseDto delete(String id);
}
