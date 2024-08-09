package woowa.team4.bff.search.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Document(indexName = "restaurants")
@AllArgsConstructor
public class RestaurantDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "restaurant_name")
    private String restaurantName;

    @Field(type = FieldType.Keyword, name = "restaurant_id")
    private String restaurantId;

    @Override
    public String toString() {
        return "RestaurantDocument{" +
                "id='" + id + '\'' +
                ", restaurantName='" + restaurantName + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                '}';
    }
}
