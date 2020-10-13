package product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import product.model.InventoryRequestDto;
import product.model.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {
    private final RestTemplate restTemplate;

    @Autowired
    public InventoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Product> findProducts(final InventoryRequestDto requestDto) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.postForEntity("http://inventory-service/product",
                requestDto, Product[].class).getBody()));

    }

}
