package me.musinsa.app.product.repository;

import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.UpdateProduct;
import me.musinsa.database.product.domain.ProductEntity;
import me.musinsa.database.product.repository.ProductJpaRepository;
import me.musinsa.database.product.repository.query.ProductDatabaseQueryRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public class ProductCommandDatabaseRepository implements ProductCommandRepository {

    private final ProductDatabaseQueryRepository queryRepository;
    private final ProductJpaRepository repository;

    public ProductCommandDatabaseRepository(ProductDatabaseQueryRepository queryRepository, ProductJpaRepository repository) {
        this.queryRepository = queryRepository;
        this.repository = repository;
    }

    @Override
    @Transactional
    public ProductEntity save(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setBrand(product.getBrand());
        productEntity.setCategory(product.getCategory());
        productEntity.setPrice(product.getPrice());
        return repository.save(lowestSetting(productEntity));
    }

    @Override
    @Transactional
    public ProductEntity update(UpdateProduct product) {
        Optional<ProductEntity> result = repository.findById(product.getId());
        if(result.isEmpty()) {
            return null;
        }
        ProductEntity productEntity = result.get();
        productEntity.setPrice(product.getPrice());
        return repository.save(lowestSetting(productEntity));
    }

    private ProductEntity lowestSetting(ProductEntity productEntity) {
        ProductEntity brandCategoryLowest = queryRepository.findBrandCategoryLowest(productEntity.getBrand(), productEntity.getCategory());
        if(brandCategoryLowest != null && brandCategoryLowest.getPrice() > productEntity.getPrice()) {
            productEntity.setBrandCategoryMin(true);
            brandCategoryLowest.setBrandCategoryMin(false);
        }

        queryRepository.findCategoryMaxAndMinPrice(productEntity.getCategory()).forEach(entity -> {
            if(entity.isCategoryMax() && entity.getPrice() < productEntity.getPrice()) {
                productEntity.setCategoryMax(true);
                entity.setCategoryMax(false);
            }
            if(entity.isCategoryMin() && entity.getPrice() > productEntity.getPrice()) {
                productEntity.setCategoryMin(true);
                entity.setCategoryMin(false);
            }
        });
        return productEntity;
    }
}
