package me.musinsa.app.product.repository;

import me.musinsa.app.product.domain.Product;
import me.musinsa.database.product.repository.query.ProductDatabaseQueryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class ProductQueryDatabaseRepository implements ProductQueryRepository {

    private final ProductDatabaseQueryRepository repository;

    public ProductQueryDatabaseRepository(ProductDatabaseQueryRepository repository) {
        this.repository = repository;
    }

    public List<Product> findLowestPrices(List<String> brands) {
        return repository.findByBrandIn(brands).stream()
                .map(entity -> new Product(entity.getBrand(), entity.getCategory(), entity.getPrice()))
                .collect(Collectors.toList());
    }

}
