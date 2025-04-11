package com.xmall.product.domain.entity;

import com.xmall.product.domain.entity.key.SKUAttributeKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "sku_regular_attribute_value")
public class SKURegularAttributeValueEntity implements Serializable {
    @EmbeddedId
    SKUAttributeKey id;

    @ManyToOne
    @MapsId("skuId")
    @JoinColumn(name = "sku_id")
    SKUEntity sku;

    @ManyToOne
    @MapsId("attributeId")
    @JoinColumn(name = "attribute_id")
    ProductAttributeEntity attribute;

    @Column(nullable = false, length = 100)
    String value;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    LocalDateTime updatedAt;
}
