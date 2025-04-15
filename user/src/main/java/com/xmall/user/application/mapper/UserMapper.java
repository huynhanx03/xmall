package com.xmall.user.application.mapper;

import com.xmall.user.application.dto.request.UserRegisterRequest;
import com.xmall.user.application.dto.response.UserRegisterResponse;
import com.xmall.user.domain.entity.UserBaseEntity;
import com.xmall.user.domain.entity.UserInfoEntity;
import com.xmall.user.domain.entity.UserVerifyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "userPassword", ignore = true)
    @Mapping(target = "userSalt", ignore = true)
    @Mapping(target = "isTwoFactorEnabled", constant = "false")
    @Mapping(target = "role", ignore = true)
    UserBaseEntity toUserBaseEntity(UserRegisterRequest request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "userState", constant = "1")
    @Mapping(target = "userGender", constant = "0")
    @Mapping(target = "userIsAuthentication", constant = "0")
    @Mapping(target = "userIntegration", constant = "0")
    @Mapping(target = "userGrowth", constant = "0")
    UserInfoEntity toUserInfoEntity(UserRegisterRequest request);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "verifyOtp", ignore = true)
    @Mapping(target = "verifyKey", ignore = true)
    @Mapping(target = "verifyKeyHash", ignore = true)
    @Mapping(target = "verifyType", constant = "1")
    @Mapping(target = "isVerified", constant = "false")
    @Mapping(target = "isDeleted", constant = "false")
    UserVerifyEntity toUserVerifyEntity(UserRegisterRequest request);

    @Mapping(target = "roleName", source = "user.role.roleName")
    @Mapping(target = "isEmailVerified", source = "verify.isVerified")
    @Mapping(target = "isPhoneVerified", source = "verify.isVerified")
    UserRegisterResponse toUserRegisterResponse(UserBaseEntity user, UserInfoEntity info, UserVerifyEntity verify);
} 