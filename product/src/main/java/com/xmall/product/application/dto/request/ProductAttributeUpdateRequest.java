package com.xmall.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeUpdateRequest implements Serializable {
    @NotBlank(message = "Attribute name is required")
    @Size(min = 2, max = 100, message = "Attribute name must be between 2 and 100 characters")
    String name;

    @NotNull(message = "Attribute type is required")
    Integer type;
} 