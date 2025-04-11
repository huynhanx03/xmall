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
public class SPUUpdateRequest implements Serializable {
    @NotBlank(message = "SPU name is required")
    @Size(min = 2, max = 100, message = "SPU name must be between 2 and 100 characters")
    String name;

    @NotNull(message = "Category ID is required")
    Long categoryId;

    @NotNull(message = "Brand ID is required")
    Long brandId;
} 