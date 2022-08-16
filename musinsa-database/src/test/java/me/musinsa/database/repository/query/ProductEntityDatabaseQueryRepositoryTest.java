package me.musinsa.database.repository.query;

import me.musinsa.database.product.domain.ProductEntity;
import me.musinsa.database.product.repository.query.ProductDatabaseQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import javax.transaction.Transactional;
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

    @Test
    @DisplayName("카테고리 이름으로 최소, 최대 가격을 조회한다.")
    void category_max_min_brand_test() {
        List<ProductEntity> result = repository.findCategoryMaxAndMinPrice("상의");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        for (ProductEntity productEntity : result) {
            if(productEntity.isCategoryMax()) {
                assertThat(productEntity.getBrand()).isEqualTo("I");
                assertThat(productEntity.getPrice()).isEqualTo(11400);
            } else if(productEntity.isCategoryMin()) {
                assertThat(productEntity.getBrand()).isEqualTo("C");
                assertThat(productEntity.getPrice()).isEqualTo(10000);
            }
        }
    }

    @Test
    @DisplayName("존재하지 않는 카테고리 조회시 빈 리스트를 리턴한다.")
    void test() {
        List<ProductEntity> result = repository.findCategoryMaxAndMinPrice("없음");
        assertThat(result).isNotNull();
        assertThat(result).hasSize(0);
    }

    @Test
    @DisplayName("입력된 브랜드 카테고리의 최저가를 조회한다. ")
    void brand_category_lowest_price() {
        ProductEntity result = repository.findBrandCategoryLowest("A", "상의");
        assertThat(result).isNotNull();
        assertThat(result.getBrand()).isEqualTo("A");
        assertThat(result.getPrice()).isEqualTo(11200);
    }


}