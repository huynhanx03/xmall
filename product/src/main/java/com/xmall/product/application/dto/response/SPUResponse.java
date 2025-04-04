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
public class SPUResponse implements Serializable {
    Long spuId;
    Long categoryId;
    String categoryName;
    Long brandId;
    String brandName;
    String name;
    String description;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 