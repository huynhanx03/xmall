package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaCategoryRepository extends JpaRepository<CategoryEntity, Long> {
    boolean existsByName(String name);  
}
