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
@Table(name = "user_base")
public class UserBaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(nullable = false, unique = true, length = 255)
    String userAccount;

    @Column(nullable = false, length = 255)
    String userPassword;

    @Column(nullable = false, length = 255)
    String userSalt;

    @Column(columnDefinition = "TINYINT(1) DEFAULT 0")
    Boolean isTwoFactorEnabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    UserRoleEntity role;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;
} 