package com.xmall.product.domain.entity.key;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class SKUAttributeKey implements Serializable {
    private Long skuId;
    private Long attributeId;
}
