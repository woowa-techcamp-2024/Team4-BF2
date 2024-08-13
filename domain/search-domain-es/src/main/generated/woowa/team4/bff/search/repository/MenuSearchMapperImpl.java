package woowa.team4.bff.search.repository;

import javax.annotation.processing.Generated;
import woowa.team4.bff.search.document.MenuSearchDocument;
import woowa.team4.bff.search.domain.MenuSearch;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-13T10:21:07+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class MenuSearchMapperImpl implements MenuSearchMapper {

    @Override
    public MenuSearch toDomain(MenuSearchDocument document) {
        if ( document == null ) {
            return null;
        }

        MenuSearch.MenuSearchBuilder menuSearch = MenuSearch.builder();

        menuSearch.id( document.getId() );
        menuSearch.menuName( document.getMenuName() );
        menuSearch.menuId( document.getMenuId() );
        menuSearch.restaurantId( document.getRestaurantId() );

        return menuSearch.build();
    }

    @Override
    public MenuSearchDocument toDocument(MenuSearch domain) {
        if ( domain == null ) {
            return null;
        }

        String id = null;
        String menuName = null;
        Long menuId = null;
        Long restaurantId = null;

        id = domain.getId();
        menuName = domain.getMenuName();
        menuId = domain.getMenuId();
        restaurantId = domain.getRestaurantId();

        MenuSearchDocument menuSearchDocument = new MenuSearchDocument( id, menuName, menuId, restaurantId );

        return menuSearchDocument;
    }
}
