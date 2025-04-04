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
public class SKUImageResponse implements Serializable {
    Long skuImageId;
    Long skuId;
    String skuName;
    String imageUrl;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 