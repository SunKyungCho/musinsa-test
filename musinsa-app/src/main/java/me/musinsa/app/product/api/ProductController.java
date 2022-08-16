package me.musinsa.app.product.api;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @PostMapping("/products")
    public ResponseEntity<String> createProduct() {
        return ResponseEntity.ok("created");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id) {
        return ResponseEntity.ok("updated");
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok("deleted");
    }
}
