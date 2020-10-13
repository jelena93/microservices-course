package product.model;

import lombok.Data;

import java.util.List;

@Data
public class InventoryResponseDto {
    private List<Product> productList;
}
