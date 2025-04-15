package com.xmall.user.infrastructure.persistence;

import com.xmall.user.domain.entity.UserBaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserBaseRepository extends JpaRepository<UserBaseEntity, Long> {
    Optional<UserBaseEntity> findByUserAccount(String userAccount);
    boolean existsByUserAccount(String userAccount);
} 