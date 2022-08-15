package me.musinsa.app.product.service;

import me.musinsa.app.product.domain.BrandCategoryLowestPrices;
import me.musinsa.app.product.domain.CategoryLowestPrices;
import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.ProductPrice;
import me.musinsa.app.product.repository.ProductQueryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ProductQueryExecutorTest {

    @Test
    @DisplayName("선택된 브랜드의 카테코리별 최저가와 총합을 조회한다.")
    void brand_category_lowest_price_and_total_price() {
        // given
        ProductQueryRepository productQueryRepository = new ProductQueryRepository() {
            @Override
            public List<Product> findBrandLowestCategory(List<String> brands) {
                return List.of(
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
            }

            @Override
            public ProductPrice findMaxAndMinBrands(String category) {
                return null;
            }
        };
        ProductQueryExecutor productQueryExecutor = new ProductQueryExecutor(productQueryRepository);
        // when
        CategoryLowestPrices categoryLowestPrices = productQueryExecutor.findLowestCategoryByBrands(List.of("A", "B", "C"));
        // then
        List<Product> prices = categoryLowestPrices.getPrices();
        assertThat(prices)
                .hasSize(8)
                .containsOnly(
                        new Product("상의", "C", 10000),
                        new Product("아우터", "A", 5500),
                        new Product("바지", "C", 3300),
                        new Product("스니커즈", "A", 9000),
                        new Product("가방", "A", 2000),
                        new Product("모자", "A", 1700),
                        new Product("양말", "A", 1800),
                        new Product("액세서리", "C", 2100)
                );
        assertThat(categoryLowestPrices.getTotal()).isEqualTo(35400);
    }

    @Test
    @DisplayName("선택된 브랜드의 최저가 카테고리의 총합을 조회한다.")
    void brand_category_min() {
        ProductQueryRepository productQueryRepository = new ProductQueryRepository() {
            @Override
            public List<Product> findBrandLowestCategory(List<String> brands) {
                return List.of(
                        new Product("상의", "D", 10100),
                        new Product("아우터", "D", 5100),
                        new Product("바지", "D", 3000),
                        new Product("스니커즈", "D", 9500),
                        new Product("가방", "D", 2500),
                        new Product("모자", "D", 1500),
                        new Product("양말", "D", 2400),
                        new Product("액세서리", "D", 2000)
                );
            }

            @Override
            public ProductPrice findMaxAndMinBrands(String category) {
                return null;
            }
        };
        ProductQueryExecutor productQueryExecutor = new ProductQueryExecutor(productQueryRepository);

        BrandCategoryLowestPrices expected = productQueryExecutor.findBrandLowestTotalPrice("D");
        assertThat(expected).isNotNull();
        assertThat(expected.brand()).isEqualTo("D");
        assertThat(expected.price()).isEqualTo(36100);
    }
}