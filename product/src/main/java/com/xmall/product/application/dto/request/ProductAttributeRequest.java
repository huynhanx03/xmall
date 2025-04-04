package com.xmall.product.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductAttributeRequest implements Serializable {
    @NotNull(message = "Name is required")
    String name;

    @NotNull(message = "Type is required")
    Integer type;
} 