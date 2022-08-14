package me.musinsa.database.product.repository;

import me.musinsa.database.product.domain.ProductEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductJpaRepository extends CrudRepository<ProductEntity, Long> {
}
