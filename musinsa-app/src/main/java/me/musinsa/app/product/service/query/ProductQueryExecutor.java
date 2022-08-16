package me.musinsa.app.product.service.query;

import me.musinsa.app.product.domain.BrandCategoryLowestPrices;
import me.musinsa.app.product.domain.CategoryLowestPrices;
import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.ProductPrice;
import me.musinsa.app.product.repository.ProductQueryRepository;

import java.util.List;


public class ProductQueryExecutor {

    private final ProductQueryRepository productQueryRepository;

    public ProductQueryExecutor(ProductQueryRepository productQueryRepository) {
        this.productQueryRepository = productQueryRepository;
    }

    public CategoryLowestPrices findLowestCategoryByBrands(List<String> brands) {
        List<Product> products = productQueryRepository.findBrandLowestCategory(brands);
        CategoryLowestPrices categoryLowestPrices = new CategoryLowestPrices();
        for (Product product : products) {
            categoryLowestPrices.add(product);
        }
        return categoryLowestPrices;
    }

    public BrandCategoryLowestPrices findBrandLowestTotalPrice(String brand) {
        List<Product> products = productQueryRepository.findBrandLowestCategory(List.of(brand));
        int sum = products.stream()
                .mapToInt(Product::getPrice)
                .sum();
        return new BrandCategoryLowestPrices(brand, sum);
    }

    public ProductPrice findMaxMinBrandByCategory(String category) {
        return productQueryRepository.findMaxAndMinBrands(category);
    }
}
