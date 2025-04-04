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
public class SKURegularAttributeValueResponse implements Serializable {
    Long skuId;
    String skuName;
    Long attributeId;
    String attributeName;
    String value;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 