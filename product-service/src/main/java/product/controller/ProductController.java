package product.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import product.model.InventoryRequestDto;
import product.model.Product;
import product.model.RequestDto;
import product.service.CatalogService;
import product.service.InventoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {
    private final CatalogService catalogService;
    private final InventoryService inventoryService;

    @Autowired
    public ProductController(CatalogService catalogService, InventoryService inventoryService) {
        this.catalogService = catalogService;
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @HystrixCommand(fallbackMethod = "doFallback",
            commandProperties = {
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "100"),
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "3000"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "2"),
                    @HystrixProperty(name = HystrixPropertiesManager.CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "1000"),
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY, value = "SEMAPHORE")
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "2")
            })
    public ResponseEntity findProductById(@RequestBody RequestDto requestDto) {
        final List<Product> productList = new ArrayList<>();
        for (String uniqueIdentifier : requestDto.getUniqueIdentifiers()) {
            productList.add(catalogService.findProductById(uniqueIdentifier));
        }

        for (String s : requestDto.getSkuList()) {
            List<Product> productsBySku = catalogService.findProductsBySku(s);
            productList.addAll(productsBySku);
        }

        final InventoryRequestDto inventoryRequestDto = new InventoryRequestDto();
        inventoryRequestDto.getUniqueIdentifiers()
                .addAll(productList.stream()
                        .map(Product::getId)
                        .collect(Collectors.toSet()));

        List<Product> products = inventoryService.findProducts(inventoryRequestDto);

        return new ResponseEntity<List>(products.stream()
                .filter(p -> p.getCount() > 0)
                .collect(Collectors.toList()), HttpStatus.OK);

    }

    @HystrixCommand
    public ResponseEntity doFallback(RequestDto requestDto, Throwable e) throws Throwable {
        if (e.getMessage() != null && e.getMessage().startsWith("No instances available for")) {
            return new ResponseEntity("Service unavailable", HttpStatus.SERVICE_UNAVAILABLE);
        }
        throw e;
    }

}
