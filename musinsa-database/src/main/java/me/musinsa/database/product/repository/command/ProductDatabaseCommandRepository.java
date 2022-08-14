package me.musinsa.database.product.repository.command;

import me.musinsa.database.product.domain.ProductEntity;
import me.musinsa.database.product.repository.ProductJpaRepository;

public class ProductDatabaseCommandRepository {
    private final ProductJpaRepository productJpaRepository;

    public ProductDatabaseCommandRepository(ProductJpaRepository productJpaRepository) {
        this.productJpaRepository = productJpaRepository;
    }

    public void save(ProductEntity productEntity) {
        productJpaRepository.save(productEntity);
    }

    public void update(ProductEntity productEntity) {
        productJpaRepository.save(productEntity);
    }

    public void delete(ProductEntity productEntity) {
        productJpaRepository.delete(productEntity);
    }
}
