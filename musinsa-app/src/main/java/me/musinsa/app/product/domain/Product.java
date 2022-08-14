package me.musinsa.app.product.domain;

public class Product {

    private final String category;
    private final String brand;
    private final int price;

    public Product(String category, String brand, int price) {
        this.category = category;
        this.brand = brand;
        this.price = price;
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
    public boolean lessThan(Product product) {
        return false;
    }
}
