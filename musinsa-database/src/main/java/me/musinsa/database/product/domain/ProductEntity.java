package me.musinsa.database.product.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "product")
@NoArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long productId;
    private String category;
    private String brand;
    private int price;
    private boolean categoryMin;
    private boolean categoryMax;
    private boolean brandCategoryMin;

    public long getProductId() {
        return productId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCategoryMin(boolean categoryMin) {
        this.categoryMin = categoryMin;
    }

    public void setCategoryMax(boolean categoryMax) {
        this.categoryMax = categoryMax;
    }

    public void setBrandCategoryMin(boolean brandCategoryMin) {
        this.brandCategoryMin = brandCategoryMin;
    }

    public String getCategory() {
        return category;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public boolean isCategoryMin() {
        return categoryMin;
    }

    public boolean isCategoryMax() {
        return categoryMax;
    }

    public boolean isBrandCategoryMin() {
        return brandCategoryMin;
    }
}
