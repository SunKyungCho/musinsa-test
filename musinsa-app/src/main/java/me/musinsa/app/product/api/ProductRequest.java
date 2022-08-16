package me.musinsa.app.product.api;

import me.musinsa.app.product.domain.Product;

public class ProductRequest {

    private String category;
    private String brand;
    private int price;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Product toProduct() {
        return new Product(category, brand, price);
    }
}
