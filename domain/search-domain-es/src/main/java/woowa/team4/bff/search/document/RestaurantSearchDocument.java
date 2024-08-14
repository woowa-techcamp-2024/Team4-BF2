package woowa.team4.bff.search.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Document(indexName = "restaurants")
@Setting(settingPath = "elasticsearch-settings.json")
@Mapping(mappingPath = "elasticsearch-restaurant-mapping.json")
@AllArgsConstructor
public class RestaurantSearchDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "restaurantName")
    private String restaurantName;

    private Long restaurantId;
}
