package com.xmall.user.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "user_verify")
public class UserVerifyEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long verifyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserBaseEntity user;

    @Column(nullable = false, length = 6)
    String verifyOtp;

    @Column(nullable = false, length = 255)
    String verifyKey;

    @Column(nullable = false, length = 255)
    String verifyKeyHash;

    @Column(nullable = false)
    Integer verifyType;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    Boolean isVerified;

    @Column(nullable = false, columnDefinition = "TINYINT(1) DEFAULT 0")
    Boolean isDeleted;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;
} 