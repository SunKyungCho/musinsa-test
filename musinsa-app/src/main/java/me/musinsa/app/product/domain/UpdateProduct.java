package me.musinsa.app.product.domain;

public class UpdateProduct {

    private long id;
    private int price;

    public UpdateProduct(long id, int price) {
        this.id = id;
        this.price = price;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
