package me.musinsa.app.product.domain;

import java.util.Objects;

public class Product {

    private long id;
    private String category;
    private String brand;
    private int price;
    private boolean categoryMin;
    private boolean categoryMax;
    private boolean brandCategoryMin;

    public Product(String category, String brand, int price) {
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public Product(long id, String brand, String category, int price) {
        this.id = id;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && price == product.price && Objects.equals(category, product.category) && Objects.equals(brand, product.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, brand, price);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public boolean isCategoryMin() {
        return categoryMin;
    }

    public void setCategoryMin(boolean categoryMin) {
        this.categoryMin = categoryMin;
    }

    public boolean isCategoryMax() {
        return categoryMax;
    }

    public void setCategoryMax(boolean categoryMax) {
        this.categoryMax = categoryMax;
    }

    public boolean isBrandCategoryMin() {
        return brandCategoryMin;
    }

    public void setBrandCategoryMin(boolean brandCategoryMin) {
        this.brandCategoryMin = brandCategoryMin;
    }
}
