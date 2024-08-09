package woowa.team4.bff.search.repository;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import woowa.team4.bff.search.document.RestaurantDocument;
import woowa.team4.bff.search.domain.Restaurant;

@Mapper
public interface RestaurantMapper {
    RestaurantMapper INSTANCE = Mappers.getMapper(RestaurantMapper.class);
    Restaurant toDomain(RestaurantDocument document);
    RestaurantDocument toDocument(Restaurant domain);
}
