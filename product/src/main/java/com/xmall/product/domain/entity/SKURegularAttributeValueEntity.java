package com.xmall.product.domain.entity;

import com.xmall.product.domain.entity.key.SKUAttributeKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

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
    SKUEntity sku;

    @ManyToOne
    @MapsId("attributeId")
    ProductAttributeEntity attribute;

    @Column(nullable = false, length = 100)
    String value;
}
