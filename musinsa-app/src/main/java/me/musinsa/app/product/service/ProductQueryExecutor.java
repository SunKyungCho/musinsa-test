package me.musinsa.app.product.service;

import me.musinsa.app.product.domain.CategoryLowestPrices;
import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.repository.ProductQueryRepository;

import java.util.List;


public class ProductQueryExecutor {

    private final ProductQueryRepository productQueryRepository;

    public ProductQueryExecutor(ProductQueryRepository productQueryRepository) {
        this.productQueryRepository = productQueryRepository;
    }

    public CategoryLowestPrices findLowestCategoryByBrands(List<String> brands) {
        List<Product> products = productQueryRepository.findLowestPrices(brands);
        CategoryLowestPrices categoryLowestPrices = new CategoryLowestPrices();
        for (Product product : products) {
            categoryLowestPrices.add(product);
        }
        return categoryLowestPrices;
    }
}
