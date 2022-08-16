package me.musinsa.app.product.repository;

import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.ProductPrice;
import me.musinsa.database.product.domain.ProductEntity;
import me.musinsa.database.product.repository.query.ProductDatabaseQueryRepository;

import java.util.List;

public class ProductQueryDatabaseRepository implements ProductQueryRepository {

    private final ProductDatabaseQueryRepository repository;

    public ProductQueryDatabaseRepository(ProductDatabaseQueryRepository repository) {
        this.repository = repository;
    }

    public List<Product> findBrandLowestCategory(List<String> brands) {
        return repository.findByBrandIn(brands).stream()
                .map(entity -> new Product(entity.getProductId(), entity.getBrand(), entity.getCategory(), entity.getPrice()))
                .toList();
    }

    @Override
    public ProductPrice findMaxAndMinBrands(String category) {
        List<ProductEntity> categoryMaxAndMinPrice = repository.findCategoryMaxAndMinPrice(category);
        ProductPrice productPrice = new ProductPrice();
        for (ProductEntity productEntity : categoryMaxAndMinPrice) {
            if (productEntity.isCategoryMax()) {
                productPrice.setMax(new Product(productEntity.getProductId(), productEntity.getBrand(), productEntity.getCategory(), productEntity.getPrice()));
            }
            if (productEntity.isCategoryMin()) {
                productPrice.setMin(new Product(productEntity.getProductId(), productEntity.getBrand(), productEntity.getCategory(), productEntity.getPrice()));
            }
        }
        return productPrice;
    }
}
