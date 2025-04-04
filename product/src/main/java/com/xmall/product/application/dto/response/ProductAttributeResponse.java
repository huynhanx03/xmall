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
public class ProductAttributeResponse implements Serializable {
    Long attributeId;
    String name;
    Integer type;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
} 