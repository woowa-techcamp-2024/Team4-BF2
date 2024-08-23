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
import woowa.team4.bff.search.domain.RestaurantMenusSearch;
import woowa.team4.bff.search.domain.RestaurantMenusSearch.Menu;

import java.util.List;
import java.util.stream.Collectors;

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

    public static RestaurantMenusDocument from(RestaurantMenusSearch restaurantMenusSearch) {
        return RestaurantMenusDocument.builder()
                .id(restaurantMenusSearch.getId())
                .restaurantName(restaurantMenusSearch.getRestaurantName())
                .deliveryLocation(restaurantMenusSearch.getDeliveryLocation())
                .restaurantId(restaurantMenusSearch.getRestaurantId())
                .menus(restaurantMenusSearch.getMenus()
                        .stream()
                        .map(MenuDocument::from)
                        .toList())
                .build();
    }

    public RestaurantMenusSearch toDomain() {
        return RestaurantMenusSearch.builder()
                .id(id)
                .restaurantName(restaurantName)
                .deliveryLocation(deliveryLocation)
                .restaurantId(restaurantId)
                .menus(menus.stream()
                        .map(MenuDocument::toDomain)
                        .collect(Collectors.toList()))
                .build();
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
    }
}
