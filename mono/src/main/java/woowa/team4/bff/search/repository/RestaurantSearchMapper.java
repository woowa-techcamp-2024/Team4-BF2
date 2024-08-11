package woowa.team4.bff.search.repository;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import woowa.team4.bff.search.document.RestaurantSearchDocument;
import woowa.team4.bff.search.domain.RestaurantSearch;

@Mapper
public interface RestaurantSearchMapper {
    RestaurantSearchMapper INSTANCE = Mappers.getMapper(RestaurantSearchMapper.class);
    RestaurantSearch toDomain(RestaurantSearchDocument document);
    RestaurantSearchDocument toDocument(RestaurantSearch domain);
}
