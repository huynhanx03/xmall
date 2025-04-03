package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBrandRepository extends JpaRepository<BrandEntity, Long> {
    boolean existsByName(String name);
}
