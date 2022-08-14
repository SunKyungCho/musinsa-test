package me.musinsa.app.product.service;

import me.musinsa.app.product.domain.CategoryLowestPrices;
import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.repository.ProductQueryRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductQueryExecutorTest {

    @Test
    void test() {
        ProductQueryRepository productQueryRepository = brands -> List.of(
                new Product("상의", "A", 11200),
                new Product("상의", "A", 12200),
                new Product("아우터", "A", 5500),
                new Product("바지", "A", 4200),
                new Product("스니커즈", "A", 9000),
                new Product("가방", "A", 2000),
                new Product("모자", "A", 1700),
                new Product("양말", "A", 1800),
                new Product("액세서리", "A", 2300),
                new Product("상의", "B", 10500),
                new Product("아우터", "B", 5900),
                new Product("바지", "B", 3800),
                new Product("스니커즈", "B", 9100),
                new Product("가방", "B", 2100),
                new Product("모자", "B", 2000),
                new Product("양말", "B", 2000),
                new Product("액세서리", "B", 2200),
                new Product("상의", "C", 10000),
                new Product("아우터", "C", 6200),
                new Product("바지", "C", 3300),
                new Product("스니커즈", "C", 9200),
                new Product("가방", "C", 2200),
                new Product("모자", "C", 1900),
                new Product("양말", "C", 2200),
                new Product("액세서리", "C", 2100)
        );
        ProductQueryExecutor productQueryExecutor = new ProductQueryExecutor(productQueryRepository);
        CategoryLowestPrices categoryLowestPrices = productQueryExecutor.findLowestCategoryByBrands(List.of("A"));
        List<Product> prices = categoryLowestPrices.getPrices();
        assertThat(prices).hasSize(8);
        assertThat(categoryLowestPrices.getTotal()).isEqualTo(35400);
    }

}