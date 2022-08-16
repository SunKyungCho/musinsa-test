package me.musinsa.app.product.service.query;

import me.musinsa.app.product.domain.BrandCategoryLowestPrices;
import me.musinsa.app.product.domain.CategoryLowestPrices;
import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.ProductPrice;
import me.musinsa.app.product.service.query.ProductQueryExecutor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ProductQueryExecutorTest {

    @Autowired
    ProductQueryExecutor productQueryExecutor;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @Sql("/sql/product_sample.sql")
    @DisplayName("선택된 브랜드의 카테코리별 최저가와 총합을 조회한다.")
    void brand_category_lowest_price_and_total_price() {

        // when
        CategoryLowestPrices categoryLowestPrices = productQueryExecutor.findLowestCategoryByBrands(List.of("A", "B", "C"));

        // then
        List<Product> prices = categoryLowestPrices.getPrices();
        assertThat(prices).hasSize(8);
        assertThat(categoryLowestPrices.getTotal()).isEqualTo(35400);

    }

    @Test
    @Sql("/sql/product_sample.sql")
    @DisplayName("선택된 브랜드의 최저가 카테고리의 총합을 조회한다.")
    void brand_category_min() {
        BrandCategoryLowestPrices expected = productQueryExecutor.findBrandLowestTotalPrice("D");
        assertThat(expected).isNotNull();
        assertThat(expected.brand()).isEqualTo("D");
        assertThat(expected.price()).isEqualTo(36100);
    }

    @Test
    @DisplayName("케테고리 이름으로 최소, 최대 가격을 조회한다")
    void brand_category_min_max() {

        //given
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (100, '상의', 11200, 'A', 0,1,1)");
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (200, '상의', 10500, 'B', 0,0,1)");
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (300, '상의', 10000, 'C', 1,0,1)");
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (400, '상의', 10100, 'D', 0,0,1)");
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (500, '상의', 10700, 'E', 0,0,1)");

        ProductPrice productPrice = productQueryExecutor.findMaxMinBrandByCategory("상의");
        Product max = productPrice.getMax();
        Product min = productPrice.getMin();
        assertThat(max).isNotNull();
        assertThat(max.getId()).isEqualTo(100L);
        assertThat(max.getPrice()).isEqualTo(11200);

        assertThat(min).isNotNull();
        assertThat(min.getId()).isEqualTo(300L);
        assertThat(min.getPrice()).isEqualTo(10000);
    }
}