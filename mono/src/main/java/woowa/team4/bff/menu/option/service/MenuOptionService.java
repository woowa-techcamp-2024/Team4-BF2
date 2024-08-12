package woowa.team4.bff.menu.option.service;

import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.item.exception.MenuNotFoundException;
import woowa.team4.bff.menu.item.repository.MenuRepository;
import woowa.team4.bff.menu.option.dto.create.MenuOptionCreateDto;
import woowa.team4.bff.menu.option.entity.MenuOption;
import woowa.team4.bff.menu.option.entity.MenuOptionDetail;
import woowa.team4.bff.menu.option.event.MenuOptionCreateEvent;
import woowa.team4.bff.menu.option.repository.MenuOptionDetailRepository;
import woowa.team4.bff.menu.option.repository.MenuOptionRepository;

@Service
@RequiredArgsConstructor
public class MenuOptionService {

    private final ApplicationEventPublisher eventPublisher;
    private final MenuRepository menuRepository;
    private final MenuOptionRepository menuOptionRepository;
    private final MenuOptionDetailRepository menuOptionDetailRepository;

    @Transactional
    public String createMenuOption(final MenuOptionCreateDto dto) {
        Long menuId = menuRepository.findIdByUuid(dto.getMenuUuid())
                .orElseThrow(() -> new MenuNotFoundException(dto.getMenuUuid()));
        MenuOption menuOption = MenuOption.create(menuId, dto);
        MenuOption savedMenuOption = menuOptionRepository.save(menuOption);

        List<MenuOptionDetail> details = dto.getOptionDetails().stream()
                .map(detailDto -> MenuOptionDetail.create(savedMenuOption.getId(),
                        detailDto.getName(), detailDto.getPrice()))
                .toList();
        List<MenuOptionDetail> savedDetails = menuOptionDetailRepository.saveAll(details);

        eventPublisher.publishEvent(MenuOptionCreateEvent.of(savedMenuOption, savedDetails));
        return savedMenuOption.getUuid();
    }
}
