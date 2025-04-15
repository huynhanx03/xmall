package com.xmall.user.infrastructure.persistence;

import com.xmall.user.domain.entity.UserInfoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaUserInfoRepository extends JpaRepository<UserInfoEntity, Long> {
    boolean existsByUserEmail(String email);
    boolean existsByUserMobile(String phone);

    @Query("SELECT u FROM UserInfoEntity u WHERE u.infoId BETWEEN :startId AND :endId")
    List<UserInfoEntity> findByIdRange(@Param("startId") Long startId, @Param("endId") Long endId);
    
    @Query("SELECT u FROM UserInfoEntity u WHERE u.infoId BETWEEN :startId AND :endId")
    Page<UserInfoEntity> findByIdRange(@Param("startId") Long startId, @Param("endId") Long endId, Pageable pageable);
    
    @Query("SELECT MIN(u.infoId) FROM UserInfoEntity u WHERE u.infoId > :lastId")
    Optional<Long> findNextId(@Param("lastId") Long lastId);
    
    @Query("SELECT MAX(u.infoId) FROM UserInfoEntity u")
    Optional<Long> findMaxId();
}