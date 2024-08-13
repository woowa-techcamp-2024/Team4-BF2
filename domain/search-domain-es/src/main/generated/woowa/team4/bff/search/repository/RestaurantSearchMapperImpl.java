package woowa.team4.bff.search.repository;

import javax.annotation.processing.Generated;
import woowa.team4.bff.search.document.RestaurantSearchDocument;
import woowa.team4.bff.search.domain.RestaurantSearch;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-08-13T11:49:35+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
public class RestaurantSearchMapperImpl implements RestaurantSearchMapper {

    @Override
    public RestaurantSearch toDomain(RestaurantSearchDocument document) {
        if ( document == null ) {
            return null;
        }

        RestaurantSearch.RestaurantSearchBuilder restaurantSearch = RestaurantSearch.builder();

        restaurantSearch.id( document.getId() );
        restaurantSearch.restaurantName( document.getRestaurantName() );
        restaurantSearch.restaurantId( document.getRestaurantId() );

        return restaurantSearch.build();
    }

    @Override
    public RestaurantSearchDocument toDocument(RestaurantSearch domain) {
        if ( domain == null ) {
            return null;
        }

        String id = null;
        String restaurantName = null;
        Long restaurantId = null;

        id = domain.getId();
        restaurantName = domain.getRestaurantName();
        restaurantId = domain.getRestaurantId();

        RestaurantSearchDocument restaurantSearchDocument = new RestaurantSearchDocument( id, restaurantName, restaurantId );

        return restaurantSearchDocument;
    }
}
