package product.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InventoryRequestDto {
    private List<String> uniqueIdentifiers = new ArrayList<>();

}
