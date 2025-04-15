package com.xmall.user.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.user.application.dto.request.UserRegisterRequest;
import com.xmall.user.application.dto.response.UserRegisterResponse;
import com.xmall.user.domain.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    public ApiResponse<UserRegisterResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        return ApiResponse.success(userService.register(request));
    }

    @GetMapping("/check-account/{account}")
    public ApiResponse<Boolean> checkAccountExists(@PathVariable String account) {
        return ApiResponse.success(userService.isAccountExists(account));
    }

    @GetMapping("/check-email/{email}")
    public ApiResponse<Boolean> checkEmailExists(@PathVariable String email) {
        return ApiResponse.success(userService.isEmailExists(email));
    }

    @GetMapping("/check-phone/{phone}")
    public ApiResponse<Boolean> checkPhoneExists(@PathVariable String phone) {
        return ApiResponse.success(userService.isPhoneExists(phone));
    }
} 