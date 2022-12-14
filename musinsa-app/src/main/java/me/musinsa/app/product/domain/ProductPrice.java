package me.musinsa.app.product.domain;

public class ProductPrice {

    private Product max;
    private Product min;

    public ProductPrice() {
    }

    public void setMax(Product max) {
        this.max = max;
    }

    public void setMin(Product min) {
        this.min = min;
    }

    public Product getMax() {
        return max;
    }

    public Product getMin() {
        return min;
    }

    public boolean hasMax() {
        return max != null;
    }
    public boolean hasMin() {
        return min != null;
    }
}
