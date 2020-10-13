package product.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Data
public class RequestDto {
    private Set<String> uniqueIdentifiers = new HashSet<>();
    private Set<String> skuList = new HashSet<>();

}
