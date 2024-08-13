package woowa.team4.bff.menu.category.repository;

import static woowa.team4.bff.domain.common.utils.PrefixedUuidConverter.*;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.category.domain.MenuCategory;
import woowa.team4.bff.menu.category.exception.MenuCategoryNotFoundException;

@Repository
@RequiredArgsConstructor
public class MenuCategoryRepository {

    private final MenuCategoryMapper menuCategoryMapper;
    private final JpaMenuCategoryRepository jpaMenuCategoryRepository;

    public MenuCategory findByUuid(final String uuid) {
        UUID extractedUuid = extractUUID(uuid);
        return menuCategoryMapper.entityToDomain(jpaMenuCategoryRepository.findByUuid(extractedUuid)
                .orElseThrow(() -> new MenuCategoryNotFoundException(uuid)));
    }

    public MenuCategory save(final MenuCategory menuCategory) {
        return menuCategoryMapper.entityToDomain(
                jpaMenuCategoryRepository.save(menuCategoryMapper.domainToEntity(menuCategory)));
    }

    public void delete(final MenuCategory menuCategory) {
        jpaMenuCategoryRepository.delete(menuCategoryMapper.domainToEntity(menuCategory));
    }
}
