package com.xmall.product.application.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SPUImageResponse implements Serializable {
    Long spuImageId;
    Long spuId;
    String spuName;
    String imageUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 