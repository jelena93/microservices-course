package catalog.repository;

import catalog.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findAllBySku(final String sku);
}
