package com.xmall.user.presentation.controller;

import com.xmall.common.application.dto.response.ApiResponse;
import com.xmall.user.application.dto.request.UserRoleCreateRequest;
import com.xmall.user.application.dto.request.UserRoleUpdateRequest;
import com.xmall.user.application.dto.response.UserRoleResponse;
import com.xmall.user.domain.service.IUserRoleService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {
    IUserRoleService roleService;

    @GetMapping
    public ApiResponse<List<UserRoleResponse>> getAll() {
        return ApiResponse.<List<UserRoleResponse>>builder()
                .data(roleService.getRoles())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserRoleResponse> getById(@PathVariable Long id) {
        return ApiResponse.<UserRoleResponse>builder()
                .data(roleService.getRole(id))
                .build();
    }

    @PostMapping
    public ApiResponse<UserRoleResponse> create(@Valid @RequestBody UserRoleCreateRequest request) {
        return ApiResponse.<UserRoleResponse>builder()
                .data(roleService.createRole(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserRoleResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody UserRoleUpdateRequest request) {
        return ApiResponse.<UserRoleResponse>builder()
                .data(roleService.updateRole(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ApiResponse.<Void>builder().build();
    }
}