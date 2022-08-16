package me.musinsa.app.product.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.musinsa.app.product.domain.BrandCategoryLowestPrices;
import me.musinsa.app.product.domain.Product;
import me.musinsa.app.product.domain.ProductPrice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql("/sql/product_sample.sql")
class BrandLowestCategoryControllerTest {

    RestTemplate restTemplate;
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("모든 카테고리의 상품을 브랜드 별로 자유롭게 선택해서 모든 상품을 구매할때 최저가 조회 API")
    void brands_category_lowest_api_test() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/brands/category/lowest")
                .queryParam("brands", "A,B,C,D");

        ResponseEntity<CategoryLowestPricesTest> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                CategoryLowestPricesTest.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        CategoryLowestPricesTest body = response.getBody();
        assertThat(body.getPrices().size()).isEqualTo(8);
        assertThat(body.getTotal()).isEqualTo(34400);
    }

    @Test
    @DisplayName("한 브랜드에서 모든 카테고리의 상품을 한꺼번에 구매 할 경우 최저가 및 브랜드 조회 API")
    void brand_lowest_total() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/brands/lowest/total")
                .queryParam("brand", "D");

        ResponseEntity<BrandCategoryLowestPrices> response = restTemplate.exchange(
                uriBuilder.toUriString(),
                HttpMethod.GET,
                request,
                BrandCategoryLowestPrices.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        BrandCategoryLowestPrices body = response.getBody();
        assertThat(body.brand()).isEqualTo("D");
        assertThat(body.price()).isEqualTo(36100);
    }

    @Test
    @DisplayName("각 카테고리 이름으로 최소,최대 가격 조회 API")
    void category_min_max_api() throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity request = new HttpEntity(headers);
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/category/min/max")
                .queryParam("category", "상의")
                .build();

        ResponseEntity<ProductPrice> response = restTemplate.exchange(
                uriComponents.toUriString(),
                HttpMethod.GET,
                request,
                ProductPrice.class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ProductPrice body = response.getBody();
        Product max = body.getMax();
        Product min = body.getMin();
        assertThat(max.getBrand()).isEqualTo("I");
        assertThat(max.getPrice()).isEqualTo(11400);
        assertThat(min.getBrand()).isEqualTo("C");
        assertThat(min.getPrice()).isEqualTo(10000);
    }


    static class ProductResponse {
        private String brand;
        private String category;
        private int price;

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }
    }

    static class CategoryLowestPricesTest {
        private List<ProductResponse> prices;
        private int total;

        public List<ProductResponse> getPrices() {
            return prices;
        }

        public void setPrices(List<ProductResponse> prices) {
            this.prices = prices;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

}