package woowa.team4.bff.menu.item.repository;

import static woowa.team4.bff.domain.common.utils.PrefixedUuidConverter.*;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import woowa.team4.bff.menu.item.domain.Menu;
import woowa.team4.bff.menu.item.entity.MenuEntity;

@Mapper(componentModel = "spring")
public interface MenuMapper {

    @Mapping(target = "uuid", source = "uuid", qualifiedByName = "uuidToMenuString")
    Menu entityToDomain(MenuEntity entity);

    @Mapping(target = "uuid", source = "uuid", qualifiedByName = "menuStringToUuid")
    MenuEntity domainToEntity(Menu domain);

    @Named("uuidToMenuString")
    default String uuidToMenuString(UUID uuid) {
        return addPrefix("menu", uuid);
    }

    @Named("menuStringToUuid")
    default UUID menuStringToUuid(String str) {
        return extractUUID(str);
    }
}
