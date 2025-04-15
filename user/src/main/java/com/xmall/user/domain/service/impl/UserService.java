package com.xmall.user.domain.service.impl;

import com.xmall.common.shared.exception.AppException;
import com.xmall.common.shared.exception.ErrorCode;
import com.xmall.user.application.dto.request.UserRegisterRequest;
import com.xmall.user.application.dto.response.UserRegisterResponse;
import com.xmall.user.application.mapper.UserMapper;
import com.xmall.user.domain.entity.UserBaseEntity;
import com.xmall.user.domain.entity.UserInfoEntity;
import com.xmall.user.domain.entity.UserRoleEntity;
import com.xmall.user.domain.entity.UserVerifyEntity;
import com.xmall.user.domain.service.IUserExistenceChecker;
import com.xmall.user.domain.service.IUserService;
import com.xmall.user.infrastructure.persistence.JpaUserBaseRepository;
import com.xmall.user.infrastructure.persistence.JpaUserInfoRepository;
import com.xmall.user.infrastructure.persistence.JpaUserRoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService implements IUserService {
    JpaUserBaseRepository userBaseRepository;
    JpaUserInfoRepository userInfoRepository;
    JpaUserRoleRepository userRoleRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    IUserExistenceChecker userExistenceChecker;

    @Override
    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {
        // Parallel check using Bloom Filter
        CompletableFuture<Boolean> emailCheck = CompletableFuture.supplyAsync(() -> 
            userExistenceChecker.mightEmailExist(request.getEmail()));
        CompletableFuture<Boolean> phoneCheck = CompletableFuture.supplyAsync(() -> 
            userExistenceChecker.mightPhoneExist(request.getPhone()));
        CompletableFuture<Boolean> userAccountCheck = CompletableFuture.supplyAsync(() ->
            userExistenceChecker.mightUserAccountExist(request.getUserAccount()));

        // Wait for both checks to complete
        boolean emailExists = emailCheck.join();
        boolean phoneExists = phoneCheck.join();
        boolean userAccountExists = userAccountCheck.join();

        // If Bloom Filter indicates possible existence, verify with database
        if (emailExists || isEmailExists(request.getEmail())) {
            ErrorCode errorCode = ErrorCode.EXISTED;
            errorCode.setMessage("Email already exists.");
            throw new AppException(errorCode);
        }

        if (phoneExists || isPhoneExists(request.getPhone())) {
            ErrorCode errorCode = ErrorCode.EXISTED;
            errorCode.setMessage("Phone already exists.");
            throw new AppException(errorCode);
        }

        if (userAccountExists || isAccountExists(request.getUserAccount())) {
            ErrorCode errorCode = ErrorCode.EXISTED;
            errorCode.setMessage("User account already exists.");
            throw new AppException(errorCode);
        }

        // Get default role
        UserRoleEntity defaultRole = userRoleRepository.findByRoleName("User")
                .orElseThrow(() -> {
                    ErrorCode errorCode = ErrorCode.NOT_EXISTED;
                    errorCode.setMessage("Default role not found.");
                    return new AppException(errorCode);
                });

        // Create user base
        UserBaseEntity userBase = userMapper.toUserBaseEntity(request);
        userBase.setUserSalt(UUID.randomUUID().toString());
        userBase.setUserPassword(passwordEncoder.encode(request.getUserPassword() + userBase.getUserSalt()));
        userBase.setRole(defaultRole);
        userBase = userBaseRepository.save(userBase);

        // Create user info
        UserInfoEntity userInfo = userMapper.toUserInfoEntity(request);
        userInfo.setUser(userBase);
        userInfo = userInfoRepository.save(userInfo);

        // Create user verify
        UserVerifyEntity userVerify = userMapper.toUserVerifyEntity(request);
        userVerify.setUser(userBase);
//        userVerify = userVerifyRepository.save(userVerify);

        // Add to Bloom Filter asynchronously
        CompletableFuture.runAsync(() -> {
            userExistenceChecker.addEmail(request.getEmail());
            userExistenceChecker.addPhone(request.getPhone());
            userExistenceChecker.addUserAccount(request.getUserAccount());
        });

        return userMapper.toUserRegisterResponse(userBase, userInfo, userVerify);
    }

    @Override
    public boolean isAccountExists(String userAccount) {
        return userBaseRepository.existsByUserAccount(userAccount);
    }

    @Override
    public boolean isEmailExists(String email) {
        return userInfoRepository.existsByUserEmail(email);
    }

    @Override
    public boolean isPhoneExists(String phone) {
        return userInfoRepository.existsByUserEmail(phone);
    }

//    @Override
//    @Transactional
//    public void updateLoginInfo(Long userId, String loginIp) {
//        userBaseRepository.findById(userId).ifPresent(user -> {
//            user.setUserLoginTime(LocalDateTime.now());
//            user.setUserLoginIp(loginIp);
//            userBaseRepository.save(user);
//        });
//    }
//
//    @Override
//    @Transactional
//    public void updateLogoutInfo(Long userId) {
//        userBaseRepository.findById(userId).ifPresent(user -> {
//            user.setUserLogoutTime(LocalDateTime.now());
//            userBaseRepository.save(user);
//        });
//    }
} 