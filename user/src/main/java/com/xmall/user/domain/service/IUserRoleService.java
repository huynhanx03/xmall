package com.xmall.user.domain.service;

import com.xmall.user.application.dto.request.UserRoleCreateRequest;
import com.xmall.user.application.dto.request.UserRoleUpdateRequest;
import com.xmall.user.application.dto.response.UserRoleResponse;
import java.util.List;

public interface IUserRoleService {
    List<UserRoleResponse> getRoles();
    UserRoleResponse getRole(Long id);
    UserRoleResponse createRole(UserRoleCreateRequest request);
    UserRoleResponse updateRole(Long id, UserRoleUpdateRequest request);
    void deleteRole(Long id);
}