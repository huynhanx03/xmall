package com.xmall.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SKURegularAttributeValueUpdateRequest implements Serializable {
    @NotNull(message = "SKU ID is required")
    Long skuId;

    @NotNull(message = "Attribute ID is required")
    Long attributeId;

    @NotBlank(message = "Value is required")
    String value;
} 