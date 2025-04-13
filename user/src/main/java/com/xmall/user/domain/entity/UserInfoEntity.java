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
@Table(name = "user_info")
public class UserInfoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long infoId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserBaseEntity user;

    @Column(length = 100)
    String firstName;

    @Column(length = 100)
    String lastName;

    @Column(length = 255)
    String email;

    @Column(length = 20)
    String phone;

    @Column(length = 500)
    String address;

    @Column(length = 100)
    String city;

    @Column(length = 100)
    String state;

    @Column(length = 20)
    String zipCode;

    @Column(length = 100)
    String country;

    @Column(columnDefinition = "TEXT")
    String profilePicture;

    @Column(columnDefinition = "TEXT")
    String bio;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;
} 