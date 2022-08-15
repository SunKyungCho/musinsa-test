package me.musinsa.app.product.repository;

import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.ProductPrice;

import java.util.List;

public interface ProductQueryRepository {

    List<Product> findBrandLowestCategory(List<String> brands);

    ProductPrice findMaxAndMinBrands(String category);
}
