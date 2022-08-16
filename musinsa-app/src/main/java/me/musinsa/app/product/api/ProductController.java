package me.musinsa.app.product.api;


import me.musinsa.app.product.domain.UpdateProduct;
import me.musinsa.app.product.service.command.ProductCommandExecutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductCommandExecutor productCommandExecutor;

    public ProductController(ProductCommandExecutor productCommandExecutor) {
        this.productCommandExecutor = productCommandExecutor;
    }

    @PostMapping("/products")
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest request) {
        productCommandExecutor.save(request.toProduct());
        return ResponseEntity.ok("created");
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable String id, @RequestBody ProductRequest request) {
        UpdateProduct updateProduct = new UpdateProduct(Long.parseLong(id), request.getPrice());
        productCommandExecutor.update(updateProduct);
        return ResponseEntity.ok("updated");
    }
}
