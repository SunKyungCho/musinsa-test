package me.musinsa.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({
        "me.musinsa.app",
        "me.musinsa.database.product",
})
@EntityScan(basePackages = {"me.musinsa.database.product.domain"})
@EnableJpaRepositories(basePackages = {"me.musinsa.database.product.repository"})
public class MusinsaAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MusinsaAppApplication.class, args);
    }

}
