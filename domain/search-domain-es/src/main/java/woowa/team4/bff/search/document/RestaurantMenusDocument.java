package woowa.team4.bff.search.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;
import woowa.team4.bff.search.domain.RestaurantMenusSearch.Menu;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@Document(indexName = "restaurants_menus")
@Setting(settingPath = "elasticsearch-settings.json")
@Mapping(mappingPath = "elasticsearch-restaurant-nested-mapping.json")
public class RestaurantMenusDocument {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "restaurantName")
    private String restaurantName;

    @Field(type = FieldType.Keyword, name = "deliveryLocation")
    private String deliveryLocation;

    private Long restaurantId;

    @Field(type = FieldType.Nested, name = "menus")
    private List<MenuDocument> menus;

    public void updateRestaurantName(String newName) {
        this.restaurantName = newName;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MenuDocument {

        @Field(type = FieldType.Text, name = "menuName")
        private String menuName;

        private Long menuId;

        public static MenuDocument from(Menu menu) {
            return MenuDocument.builder()
                    .menuName(menu.getMenuName())
                    .menuId(menu.getMenuId())
                    .build();
        }

        public Menu toDomain() {
            return Menu.builder()
                    .menuName(menuName)
                    .menuId(menuId)
                    .build();
        }

        public void updateMenuName(String newName) {
            this.menuName = newName;
        }
    }
}
