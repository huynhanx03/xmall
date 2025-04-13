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
@Table(name = "user_level")
public class UserLevelEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long levelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserBaseEntity user;

    @Column(nullable = false, length = 100)
    String levelName;

    @Column(nullable = false)
    Integer growthPoint;

    @Column(nullable = false)
    Integer defaultStatus;

    @Column(precision = 10, scale = 2)
    Double freeFreightPoint;

    @Column(nullable = false)
    Integer commentGrowthPoint;

    @Column(nullable = false)
    Integer privilegeFreeFreight;

    @Column(nullable = false)
    Integer privilegeUserPrice;

    @Column(nullable = false)
    Integer privilegeBirthday;

    @Column(length = 255)
    String note;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;
} 