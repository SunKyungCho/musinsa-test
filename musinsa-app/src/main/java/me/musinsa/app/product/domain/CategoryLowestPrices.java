package me.musinsa.app.product.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoryLowestPrices {

    private Map<String, Product> prices = new HashMap<>();
    private int total = 0;

    public void add(Product product) {
        if(!prices.containsKey(product.getCategory())) {
            prices.put(product.getCategory(), product);
            total += product.getPrice();
            return;
        }
        Product lowest = prices.get(product.getCategory());
        if(product.getPrice() < lowest.getPrice()) {
            total -= lowest.getPrice();
            total += product.getPrice();
            prices.put(product.getCategory(), product);
        }
    }

    public List<Product> getPrices() {
        return new ArrayList<>(prices.values());
    }

    public int getTotal() {
        return total;
    }
}
