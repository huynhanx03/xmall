package com.xmall.user.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.user.application.dto.request.UserRoleCreateRequest;
import com.xmall.user.application.dto.request.UserRoleUpdateRequest;
import com.xmall.user.application.dto.response.UserRoleResponse;
import com.xmall.user.application.mapper.UserRoleMapper;
import com.xmall.user.domain.entity.UserRoleEntity;
import com.xmall.user.domain.service.IUserRoleService;
import com.xmall.user.infrastructure.persistence.JpaUserRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserRoleService implements IUserRoleService {
    JpaUserRoleRepository roleRepository;
    UserRoleMapper roleMapper;

    @Override
    public List<UserRoleResponse> getRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toUserRoleResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserRoleResponse getRole(Long id) {
        return roleMapper.toUserRoleResponse(roleRepository.findById(id).orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Role not found.");
                    return new AppException(errorCode);
                }
        ));
    }

    @Override
    @Transactional
    public UserRoleResponse createRole(UserRoleCreateRequest request) {
        if (roleRepository.existsByRoleName(request.getRoleName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Role with name '" + request.getRoleName() + "' already exists.");
            throw new AppException(errorCode);
        }

        UserRoleEntity roleEntity = roleMapper.toUserRoleEntity(request);

        try {
            roleEntity = roleRepository.save(roleEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return roleMapper.toUserRoleResponse(roleEntity);
    }

    @Override
    @Transactional
    public UserRoleResponse updateRole(Long id, UserRoleUpdateRequest request) {
        UserRoleEntity roleEntity = roleRepository.findById(id).orElseThrow(() -> {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Role not found.");
            return new AppException(errorCode);
        });

        if (roleEntity.getRoleName().equals(request.getRoleName()) &&
                roleEntity.getRoleDescription().equals(request.getRoleDescription())) {
            return roleMapper.toUserRoleResponse(roleEntity);
        }

        if (roleRepository.existsByRoleName(request.getRoleName())) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Role with name '" + request.getRoleName() + "' already exists.");
            throw new AppException(errorCode);
        }

        roleMapper.updateUserRoleEntity(roleEntity, request);

        try {
            roleEntity = roleRepository.save(roleEntity);
        } catch (Exception e) {
            ErrorCode errorCode = ErrorCode.FAILED;
            errorCode.setMessage(e.getMessage());
            throw new AppException(errorCode);
        }

        return roleMapper.toUserRoleResponse(roleEntity);
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            ErrorCode errorCode = ErrorCode.NOT_EXISTED;
            errorCode.setMessage("Role not found.");
            throw new AppException(errorCode);
        }

        roleRepository.deleteById(id);
    }
}