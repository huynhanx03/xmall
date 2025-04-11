package com.xmall.product.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SKUImageCreateRequest implements Serializable {
    @NotNull(message = "SKU ID is required")
    Long skuId;

    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^(http|https)://.*", message = "Image URL must be a valid HTTP/HTTPS URL")
    String imageUrl;
} 