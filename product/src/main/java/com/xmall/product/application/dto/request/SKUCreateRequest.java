package com.xmall.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SKUCreateRequest implements Serializable {
    @NotBlank(message = "SKU name is required")
    @Size(min = 2, max = 100, message = "SKU name must be between 2 and 100 characters")
    String name;

    @NotNull(message = "SPU ID is required")
    Long spuId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be positive")
    Double price;

    @NotNull(message = "Stock is required")
    @Positive(message = "Stock must be positive")
    Integer stock;
} 