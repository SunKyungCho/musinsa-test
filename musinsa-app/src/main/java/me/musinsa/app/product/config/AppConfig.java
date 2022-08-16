package me.musinsa.app.product.config;

import me.musinsa.app.product.repository.ProductCommandDatabaseRepository;
import me.musinsa.app.product.repository.ProductCommandRepository;
import me.musinsa.app.product.repository.ProductQueryDatabaseRepository;
import me.musinsa.app.product.repository.ProductQueryRepository;
import me.musinsa.app.product.service.command.ProductCommandExecutor;
import me.musinsa.app.product.service.query.ProductQueryExecutor;
import me.musinsa.database.product.repository.ProductJpaRepository;
import me.musinsa.database.product.repository.query.ProductDatabaseQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ProductCommandRepository productCommandRepository(ProductDatabaseQueryRepository productDatabaseQueryRepository, ProductJpaRepository productJpaRepository) {
        return new ProductCommandDatabaseRepository(productDatabaseQueryRepository, productJpaRepository);
    }

    @Bean
    public ProductQueryRepository productQueryRepository(ProductDatabaseQueryRepository productDatabaseQueryRepository) {
        return new ProductQueryDatabaseRepository(productDatabaseQueryRepository);
    }

    @Bean
    public ProductCommandExecutor productCommandExecutor(ProductCommandRepository productCommandRepository) {
        return new ProductCommandExecutor(productCommandRepository);
    }

    @Bean
    public ProductQueryExecutor productQueryExecutor(ProductDatabaseQueryRepository productDatabaseQueryRepository) {
        return new ProductQueryExecutor(new ProductQueryDatabaseRepository(productDatabaseQueryRepository));
    }
}
