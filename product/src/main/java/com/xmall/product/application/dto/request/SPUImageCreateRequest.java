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
public class SPUImageCreateRequest implements Serializable {
    @NotNull(message = "SPU ID is required")
    Long spuId;

    @NotBlank(message = "Image URL is required")
    @Pattern(regexp = "^(http|https)://.*", message = "Image URL must be a valid HTTP/HTTPS URL")
    String imageUrl;
} 