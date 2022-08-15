package me.musinsa.database.product.repository.query;

import me.musinsa.database.product.domain.ProductEntity;
import me.musinsa.database.product.repository.ProductJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductDatabaseQueryRepository extends ProductJpaRepository {

    @Query("SELECT p FROM product p WHERE p.brand in (?1) AND p.brandCategoryMin = true")
    List<ProductEntity> findByBrandIn(List<String> brands);

    @Query("SELECT p FROM product p WHERE p.category in (?1) AND (p.categoryMin = true OR p.categoryMax = true)")
    List<ProductEntity> findCategoryMaxAndMinPrice(String category);
}
