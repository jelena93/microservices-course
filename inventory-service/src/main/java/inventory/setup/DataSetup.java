package inventory.setup;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import inventory.model.Product;
import inventory.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class DataSetup implements InitializingBean {

    private final ProductRepository productRepository;

    @Autowired
    public DataSetup(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void afterPropertiesSet() {
        List<Product> products = loadObjectList(Product.class, "/jcpenney_com-ecommerce_sample.csv");
        products.forEach(product -> product.setCount((int) (Math.random() * 100)));
        productRepository.saveAll(products);
    }

    public <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            MappingIterator<T> readValues =
                    mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            log.error("Error occurred while loading object list from file " + fileName, e);
            return Collections.emptyList();
        }
    }
}
