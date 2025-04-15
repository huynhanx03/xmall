package com.xmall.user.infrastructure.persistence;

import com.xmall.user.domain.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}
