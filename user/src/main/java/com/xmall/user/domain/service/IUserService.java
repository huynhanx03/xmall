package com.xmall.user.domain.service;

import com.xmall.user.application.dto.request.UserRegisterRequest;
import com.xmall.user.application.dto.response.UserRegisterResponse;

public interface IUserService {
    UserRegisterResponse register(UserRegisterRequest request);
    boolean isAccountExists(String userAccount);
    boolean isEmailExists(String email);
    boolean isPhoneExists(String phone);
} 