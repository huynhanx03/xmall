package com.xmall.user.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegisterResponse {
    Long userId;
    String userAccount;
    String email;
    String phone;
    String firstName;
    String lastName;
    String roleName;
    Boolean isEmailVerified;
    Boolean isPhoneVerified;
    LocalDateTime createdAt;
} 