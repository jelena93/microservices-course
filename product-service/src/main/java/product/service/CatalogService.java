package product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import product.model.Product;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CatalogService {
    private final RestTemplate restTemplate;

    @Autowired
    public CatalogService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product findProductById(final String uniqueId) {
        return restTemplate.getForObject("http://catalog-service/product/" + uniqueId, Product.class);
    }

    public List<Product> findProductsBySku(final String uniqueId) {
        return Arrays.asList(Objects.requireNonNull(restTemplate.getForObject("http://catalog-service/product?sku="
                + uniqueId, Product[].class)));
    }
}
