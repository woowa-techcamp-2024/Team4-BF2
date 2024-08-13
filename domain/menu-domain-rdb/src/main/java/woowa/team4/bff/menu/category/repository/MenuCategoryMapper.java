package woowa.team4.bff.menu.category.repository;

import static woowa.team4.bff.domain.common.utils.PrefixedUuidConverter.*;

import java.util.UUID;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import woowa.team4.bff.menu.category.domain.MenuCategory;
import woowa.team4.bff.menu.category.entity.MenuCategoryEntity;

@Mapper(componentModel = "spring")
public interface MenuCategoryMapper {

    @Mapping(target = "uuid", source = "uuid", qualifiedByName = "uuidToMenuCategoryString")
    MenuCategory entityToDomain(MenuCategoryEntity entity);

    @Mapping(target = "uuid", source = "uuid", qualifiedByName = "menuCategoryStringToUuid")
    MenuCategoryEntity domainToEntity(MenuCategory domain);

    @Named("uuidToMenuCategoryString")
    default String uuidToMenuCategoryString(UUID uuid) {
        return addPrefix("menu_category", uuid);
    }

    @Named("menuCategoryStringToUuid")
    default UUID menuCategoryStringToUuid(String str) {
        return extractUUID(str);
    }
}
