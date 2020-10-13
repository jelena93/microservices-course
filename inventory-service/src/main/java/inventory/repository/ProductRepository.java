package inventory.repository;

import inventory.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface ProductRepository extends CrudRepository<Product, String> {
    List<Product> findAllByIdIn(final Set<String> ids);
}
