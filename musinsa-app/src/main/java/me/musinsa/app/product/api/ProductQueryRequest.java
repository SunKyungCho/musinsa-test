package me.musinsa.app.product.api;

import java.util.List;

public class ProductQueryRequest {

    private String brands;

    public List<String> getBrands() {
        return List.of(brands.split(","));
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }
}
