package me.musinsa.app.product.domain;

import java.util.Objects;

public class ProductCategoryLowest {
    private String category;
    private String brand;
    private int price;

    public ProductCategoryLowest lessThan(ProductCategoryLowest productCategoryLowest) {
        if (Objects.equals(this.category, productCategoryLowest.category) && this.price < productCategoryLowest.price) {
            return this;
        } else {
            return productCategoryLowest;
        }
    }
}
