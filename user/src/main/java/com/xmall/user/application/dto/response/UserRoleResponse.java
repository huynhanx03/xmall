package com.xmall.user.application.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRoleResponse {
    Long roleId;
    String roleName;
    String roleDescription;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 