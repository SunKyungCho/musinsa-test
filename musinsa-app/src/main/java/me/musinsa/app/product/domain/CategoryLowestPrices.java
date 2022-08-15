package me.musinsa.app.product.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryLowestPrices {

    private final Map<String, Product> prices = new HashMap<>();
    private int total = 0;

    public void add(Product product) {
        if(!prices.containsKey(product.category())) {
            prices.put(product.category(), product);
            total += product.price();
            return;
        }
        Product lowest = prices.get(product.category());
        if(product.price() < lowest.price()) {
            total -= lowest.price();
            total += product.price();
            prices.put(product.category(), product);
        }
    }

    public List<Product> getPrices() {
        return new ArrayList<>(prices.values());
    }

    public int getTotal() {
        return total;
    }
}
