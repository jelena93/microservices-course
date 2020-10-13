package inventory.controller;

import inventory.model.Product;
import inventory.model.RequestDto;
import inventory.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("product")
public class InventoryController {
    private final ProductRepository productRepository;
    private final long sleepTime;

    @Autowired
    public InventoryController(ProductRepository productRepository, @Value("${sleepTime:0}") long sleepTime) {
        this.productRepository = productRepository;
        this.sleepTime = sleepTime;
    }

    @PostMapping
    public List<Product> findProductById(@RequestBody RequestDto requestDto) {
        //set delay to cause hystrix timeout
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            log.error("Error findProductById", e);
        }

        return productRepository.findAllByIdIn(requestDto.getUniqueIdentifiers());
    }

}
