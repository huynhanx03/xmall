package com.xmall.product.infrastructure.persistence;

import com.xmall.product.domain.entity.SPUEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaSPURepository extends JpaRepository<SPUEntity, Long>, JpaSpecificationExecutor<SPUEntity> {
    boolean existsByName(String name);

    @Override
    @EntityGraph(attributePaths = {"brand", "category"})
    Page<SPUEntity> findAll(Specification<SPUEntity> spec, Pageable pageable);
}