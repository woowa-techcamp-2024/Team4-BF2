package woowa.team4.bff.search.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Getter
@Document(indexName = "menus")
@AllArgsConstructor
public class MenuSearchDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "menuName")
    private String menuName;

    private Long menuId;
    private Long restaurantId;
}
