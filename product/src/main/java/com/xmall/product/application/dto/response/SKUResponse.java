package com.xmall.product.application.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SKUResponse implements Serializable {
    Long skuId;
    Long spuId;
    String spuName;
    String name;
    BigDecimal price;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 