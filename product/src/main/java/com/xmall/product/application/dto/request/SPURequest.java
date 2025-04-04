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
public class SPURequest implements Serializable {
    @NotNull(message = "Category ID is required")
    Long categoryId;

    @NotNull(message = "Brand ID is required")
    Long brandId;

    @NotBlank(message = "Name is required")
    String name;

    String description;
} 