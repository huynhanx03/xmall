package com.xmall.user.application.mapper;

import com.xmall.user.application.dto.request.UserRoleCreateRequest;
import com.xmall.user.application.dto.request.UserRoleUpdateRequest;
import com.xmall.user.application.dto.response.UserRoleResponse;
import com.xmall.user.domain.entity.UserRoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserRoleMapper {
    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserRoleEntity toUserRoleEntity(UserRoleCreateRequest request);

    UserRoleResponse toUserRoleResponse(UserRoleEntity entity);

    @Mapping(target = "roleId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserRoleEntity(@MappingTarget UserRoleEntity entity, UserRoleUpdateRequest request);
} 