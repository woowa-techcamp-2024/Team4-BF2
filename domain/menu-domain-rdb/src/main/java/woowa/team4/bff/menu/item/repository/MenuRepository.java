package woowa.team4.bff.menu.item.repository;

import static woowa.team4.bff.domain.common.utils.PrefixedUuidConverter.*;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.menu.item.domain.Menu;
import woowa.team4.bff.menu.item.exception.MenuNotFoundException;

@Repository
@RequiredArgsConstructor
public class MenuRepository {

    private final MenuMapper menuMapper;
    private final JpaMenuRepository jpaMenuRepository;

    public Menu findByUuid(final String uuid) {
        UUID extractedUUID = extractUUID(uuid);
        return menuMapper.entityToDomain(jpaMenuRepository.findByUuid(extractedUUID)
                .orElseThrow(() -> new MenuNotFoundException(uuid)));
    }

    public Menu save(final Menu menu) {
        return menuMapper.entityToDomain(jpaMenuRepository.save(menuMapper.domainToEntity(menu)));
    }

    public void delete(final Menu menu) {
        jpaMenuRepository.delete(menuMapper.domainToEntity(menu));
    }
}
