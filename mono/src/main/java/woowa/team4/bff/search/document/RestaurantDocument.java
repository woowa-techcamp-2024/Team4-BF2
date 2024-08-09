package woowa.team4.bff.search.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "restaurants")
public class RestaurantDocument {
    @Id
    private String id;

    @Field(type = FieldType.Text, name = "restaurant_name")
    private String restaurantName;

    @Field(type = FieldType.Keyword, name = "restaurant_id")
    private String restaurantId;
}
