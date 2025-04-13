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
@Table(name = "user_receive_address")
public class UserReceiveAddressEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long receiveAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    UserBaseEntity user;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, length = 20)
    String phone;

    @Column(length = 20)
    String postCode;

    @Column(length = 100)
    String province;

    @Column(length = 100)
    String city;

    @Column(length = 100)
    String region;

    @Column(nullable = false, length = 255)
    String detailAddress;

    @Column(length = 50)
    String areaCode;

    @Column(nullable = false)
    Integer defaultStatus;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;
} 