package inventory.model;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class RequestDto {
    private Set<String> uniqueIdentifiers =  new HashSet<>();
}
