package catalog.controller;

import catalog.model.Product;
import catalog.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("product")
public class CatalogController {
    private final ProductRepository productRepository;

    private final long sleepTime;

    @Autowired
    public CatalogController(ProductRepository productRepository, @Value("${sleepTime:0}") long sleepTime) {
        this.productRepository = productRepository;
        this.sleepTime = sleepTime;
    }

    @GetMapping(value = "{id}")
    public Product findProductById(@PathVariable String id) {
        //set delay which will not cause hystrix timeout
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.error("Error findProductById", e);
        }

        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No product with id " + id));
    }

    @GetMapping
    public Iterable<Product> findProductsBySku(@RequestParam String sku) {
        return productRepository.findAllBySku(sku);
    }
}
