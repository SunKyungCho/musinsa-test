package me.musinsa.app.product.api;


import me.musinsa.app.product.domain.BrandCategoryLowestPrices;
import me.musinsa.app.product.domain.CategoryLowestPrices;
import me.musinsa.app.product.domain.ProductPrice;
import me.musinsa.app.product.service.query.ProductQueryExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BrandLowestCategoryController {

    private final ProductQueryExecutor productQueryExecutor;

    public BrandLowestCategoryController(ProductQueryExecutor productQueryExecutor) {
        this.productQueryExecutor = productQueryExecutor;
    }

    @GetMapping("/brands/category/lowest")
    public ResponseEntity<CategoryLowestPrices> categoryLowestPrice(@RequestParam String brands) {
        CategoryLowestPrices lowestCategoryByBrands = productQueryExecutor.findLowestCategoryByBrands(List.of(brands.split(",")));
        return ResponseEntity.ok(lowestCategoryByBrands);
    }

    @GetMapping("/brands/lowest/total")
    public ResponseEntity<BrandCategoryLowestPrices> brandLowestTotal(@RequestParam String brand) {
        BrandCategoryLowestPrices brandLowestTotalPrice = productQueryExecutor.findBrandLowestTotalPrice(brand);
        return ResponseEntity.ok(brandLowestTotalPrice);
    }

    @GetMapping("/category/min/max")
    public ResponseEntity<ProductPrice> categoryMinMaxPrice(@RequestParam String category) {
        ProductPrice maxMinBrandByCategory = productQueryExecutor.findMaxMinBrandByCategory(category);
        return ResponseEntity.ok(maxMinBrandByCategory);
    }
}
