package me.musinsa.database.repository.query;

import me.musinsa.database.product.domain.ProductEntity;
import me.musinsa.database.product.repository.query.ProductDatabaseQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@Sql("/product_sample.sql")
class ProductEntityDatabaseQueryRepositoryTest {
    @Autowired
    ProductDatabaseQueryRepository repository;

    @Test
    @DisplayName("브랜드를 입력 받아 브랜드내의 모든 상품들 모두 조회한다")
    void find_by_brands() {
        List<ProductEntity> expected = repository.findByBrandIn(List.of("A"));
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(8);
        for (ProductEntity productEntity : expected) {
            assertThat(productEntity.getBrand()).isEqualTo("A");
        }
    }

    @Test
    @DisplayName("브랜드를 입력 받아 브랜드내의 카테고리 최저가인 상품 한개씩만 모두 조회한다")
    void find_by_brands_brand_category_lowest_price() {
        List<ProductEntity> expected = repository.findByBrandIn(List.of("A"));
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(8);
        ProductEntity productEntity = expected.get(0);
        assertThat(productEntity.getPrice()).isEqualTo(11200);
    }
}