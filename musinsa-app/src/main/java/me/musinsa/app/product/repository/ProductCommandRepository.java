package me.musinsa.app.product.repository;

import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.UpdateProduct;
import me.musinsa.database.product.domain.ProductEntity;

public interface ProductCommandRepository {

    ProductEntity save(Product product);

    ProductEntity update(UpdateProduct product);

}
