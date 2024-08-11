package woowa.team4.bff.search.repository;

import org.mapstruct.factory.Mappers;
import woowa.team4.bff.search.document.MenuSearchDocument;
import woowa.team4.bff.search.domain.MenuSearch;

public interface MenuSearchMapper {
    MenuSearchMapper INSTANCE = Mappers.getMapper(MenuSearchMapper.class);

    MenuSearch toDomain(MenuSearchDocument document);
    MenuSearchDocument toDocument(MenuSearch domain);
}
