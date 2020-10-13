package inventory.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Data
@Entity
public class Product {
    @Id
    @JsonProperty("uniq_id")
    private String id;
    private String sku;
    @JsonProperty("name_title")
    private String nameTitle;
    @Column(columnDefinition = "TEXT")
    private String description;
    @JsonProperty("list_price")
    private String listPrice;
    @JsonProperty("sale_price")
    private String salePrice;
    private String category;
    @JsonProperty("category_tree")
    private String categoryTree;
    @JsonProperty("average_product_rating")
    private String averageProductRating;
    @JsonProperty("product_url")
    @Column(columnDefinition = "TEXT")
    private String productUrl;
    @JsonProperty("product_image_urls")
    @Column(columnDefinition = "TEXT")
    private String productImageUrls;
    private String brand;
    @JsonProperty("total_number_reviews")
    private String totalNumberReviews;
    @JsonProperty("Reviews")
    @Column(columnDefinition = "TEXT")
    private String reviews;
    private int count;
}
