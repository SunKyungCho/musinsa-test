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
