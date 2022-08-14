package me.musinsa.database.product.config;

import me.musinsa.database.product.repository.command.ProductDatabaseCommandRepository;
import me.musinsa.database.product.repository.ProductJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public ProductDatabaseCommandRepository productDatabaseCommandRepository(ProductJpaRepository productJpaRepository) {
        return new ProductDatabaseCommandRepository(productJpaRepository);
    }

}
