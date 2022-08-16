package me.musinsa.app.product.service.command;

import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.UpdateProduct;
import me.musinsa.app.product.repository.ProductCommandRepository;
import me.musinsa.database.product.domain.ProductEntity;

public class ProductCommandExecutor {

    private final ProductCommandRepository productCommandRepository;

    public ProductCommandExecutor(ProductCommandRepository productCommandRepository) {
        this.productCommandRepository = productCommandRepository;
    }

    public ProductEntity save(Product product) {
        return productCommandRepository.save(product);
    }

    public ProductEntity update(UpdateProduct product) {
        return productCommandRepository.update(product);
    }

}
