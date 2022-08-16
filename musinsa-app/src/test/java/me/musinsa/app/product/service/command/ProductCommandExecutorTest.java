package me.musinsa.app.product.service.command;

import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.UpdateProduct;
import me.musinsa.app.product.repository.ProductQueryRepository;
import me.musinsa.app.product.service.command.ProductCommandExecutor;
import me.musinsa.database.product.domain.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class ProductCommandExecutorTest {

    @Autowired
    ProductCommandExecutor productCommandExecutor;

    @Autowired
    ProductQueryRepository productQueryRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("새로운 상품을 추가할 수 있다")
    void new_product_test() {
        // when
        ProductEntity saved = productCommandExecutor.save(new Product("아우터", "M", 10000));

        // then
        List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from product where product_id = ?", saved.getProductId());
        Map<String, Object> expected = results.get(0);
        assertThat(expected.get("category")).isEqualTo(saved.getCategory());
        assertThat(expected.get("brand")).isEqualTo(saved.getBrand());
        assertThat(expected.get("price")).isEqualTo(saved.getPrice());
    }

    @Test
    @DisplayName("추가되는 상품이 카테고리 최저가라면 기존 카테고리 최저가의 flag는 false여야 한다.")
    void new_category_min() {

        // given
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (100, '가방', 2000, 'A', 1,0,1)");

        // when
        ProductEntity save = productCommandExecutor.save(new Product("가방", "A", 1000));

        // then
        List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from product where product_id = 100");
        Map<String, Object> expected = results.get(0);
        assertThat(expected.get("category")).isEqualTo("가방");
        assertThat(expected.get("brand")).isEqualTo("A");
        assertThat(expected.get("price")).isEqualTo(2000);
        assertThat(expected.get("category_min")).isEqualTo(false);

        List<Map<String, Object>> savedResults = jdbcTemplate.queryForList("select * from product where product_id = ?", save.getProductId());
        Map<String, Object> savedProduct = savedResults.get(0);
        assertThat(savedProduct.get("category")).isEqualTo("가방");
        assertThat(savedProduct.get("brand")).isEqualTo("A");
        assertThat(savedProduct.get("price")).isEqualTo(1000);
        assertThat(savedProduct.get("category_min")).isEqualTo(true);
    }

    @Test
    @DisplayName("추가되는 상품이 카테고리 최고가라면 기존 카테고리 최고가의 flag는 false여야 한다.")
    void new_category_max() {
        // given
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (100, '가방', 5000, 'A', 0,1,1)");

        // when
        ProductEntity save = productCommandExecutor.save(new Product("가방", "A", 10000));

        // then
        List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from product where product_id = 100");
        Map<String, Object> expected = results.get(0);
        assertThat(expected.get("category")).isEqualTo("가방");
        assertThat(expected.get("brand")).isEqualTo("A");
        assertThat(expected.get("price")).isEqualTo(5000);
        assertThat(expected.get("category_max")).isEqualTo(false);

        List<Map<String, Object>> savedResults = jdbcTemplate.queryForList("select * from product where product_id = ?", save.getProductId());
        Map<String, Object> savedProduct = savedResults.get(0);
        assertThat(savedProduct.get("category")).isEqualTo("가방");
        assertThat(savedProduct.get("brand")).isEqualTo("A");
        assertThat(savedProduct.get("price")).isEqualTo(10000);
        assertThat(savedProduct.get("category_max")).isEqualTo(true);

    }

    @Test
    @DisplayName("추가되는 상품이 브랜드내의 카테고지상품의 최저가라면 이전 카테고리 최저가상품의 flag는 false여야 한다.")
    void new_brand_category_min() {
        // given
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (100, '가방', 2000, 'A', 0,0,1)");

        // when
        ProductEntity save = productCommandExecutor.save(new Product("가방", "A", 1000));

        // then
        List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from product where product_id = 100");
        Map<String, Object> expected = results.get(0);
        assertThat(expected.get("category")).isEqualTo("가방");
        assertThat(expected.get("brand")).isEqualTo("A");
        assertThat(expected.get("price")).isEqualTo(2000);
        assertThat(expected.get("brand_category_min")).isEqualTo(false);

        List<Map<String, Object>> savedResults = jdbcTemplate.queryForList("select * from product where product_id = ?", save.getProductId());
        Map<String, Object> savedProduct = savedResults.get(0);
        assertThat(savedProduct.get("category")).isEqualTo("가방");
        assertThat(savedProduct.get("brand")).isEqualTo("A");
        assertThat(savedProduct.get("price")).isEqualTo(1000);
        assertThat(savedProduct.get("brand_category_min")).isEqualTo(true);
    }

    @Test
    @DisplayName("변경된 상품이 브랜드내의 카테고지상품의 최저가라면 이전 카테고리 최저가상품의 flag는 false여야 한다.")
    void update_brand_category_min() {
        // given
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (100, '가방', 2000, 'A', 1,0,1)");
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (200, '가방', 5000, 'A', 0,0,0)");

        // when
        ProductEntity update = productCommandExecutor.update(new UpdateProduct(200, 1000));

        // then
        List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from product where product_id = 100");
        Map<String, Object> expected = results.get(0);
        assertThat(expected.get("category")).isEqualTo("가방");
        assertThat(expected.get("brand")).isEqualTo("A");
        assertThat(expected.get("price")).isEqualTo(2000);
        assertThat(expected.get("category_min")).isEqualTo(false);
        assertThat(expected.get("brand_category_min")).isEqualTo(false);

        List<Map<String, Object>> savedResults = jdbcTemplate.queryForList("select * from product where product_id = ?", update.getProductId());
        Map<String, Object> savedProduct = savedResults.get(0);
        assertThat(savedProduct.get("category")).isEqualTo("가방");
        assertThat(savedProduct.get("brand")).isEqualTo("A");
        assertThat(savedProduct.get("price")).isEqualTo(1000);
        assertThat(savedProduct.get("category_min")).isEqualTo(true);
        assertThat(savedProduct.get("brand_category_min")).isEqualTo(true);
    }

    @Test
    @DisplayName("변경 상품이 카테고리 최고가라면 기존 카테고리 최고가의 flag는 false여야 한다.")
    void update_category_max() {
        // given
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (100, '가방', 5000, 'A', 0,1,0)");
        jdbcTemplate.update("insert into product (product_id, category, price, brand, category_min, category_max, brand_category_min) values (200, '가방', 1000, 'A', 1,0,1)");

        // when
        ProductEntity update = productCommandExecutor.update(new UpdateProduct(200, 10000));

        // then
        List<Map<String, Object>> results = jdbcTemplate.queryForList("select * from product where product_id = 100");
        Map<String, Object> expected = results.get(0);
        assertThat(expected.get("category")).isEqualTo("가방");
        assertThat(expected.get("brand")).isEqualTo("A");
        assertThat(expected.get("price")).isEqualTo(5000);
        assertThat(expected.get("category_max")).isEqualTo(false);

        List<Map<String, Object>> savedResults = jdbcTemplate.queryForList("select * from product where product_id = ?", update.getProductId());
        Map<String, Object> savedProduct = savedResults.get(0);
        assertThat(savedProduct.get("category")).isEqualTo("가방");
        assertThat(savedProduct.get("brand")).isEqualTo("A");
        assertThat(savedProduct.get("price")).isEqualTo(10000);
        assertThat(savedProduct.get("category_max")).isEqualTo(true);
    }

    @BeforeEach
    void cleanUp() {
        jdbcTemplate.update("truncate table product");
    }
}