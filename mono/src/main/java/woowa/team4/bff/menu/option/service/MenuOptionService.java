package woowa.team4.bff.menu.option.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import woowa.team4.bff.menu.item.exception.MenuNotFoundException;
import woowa.team4.bff.menu.item.repository.MenuRepository;
import woowa.team4.bff.menu.option.dto.create.MenuOptionCreateDto;
import woowa.team4.bff.menu.option.dto.update.MenuOptionUpdateDto;
import woowa.team4.bff.menu.option.entity.MenuOption;
import woowa.team4.bff.menu.option.entity.MenuOptionDetail;
import woowa.team4.bff.menu.option.event.MenuOptionCreateEvent;
import woowa.team4.bff.menu.option.event.MenuOptionDeleteEvent;
import woowa.team4.bff.menu.option.event.MenuOptionUpdateEvent;
import woowa.team4.bff.menu.option.exception.MenuOptionNotFoundException;
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
        Long menuId = menuRepository.findIdByUuid(dto.menuUuid())
                .orElseThrow(() -> new MenuNotFoundException(dto.menuUuid()));
        MenuOption menuOption = MenuOption.create(menuId, dto);
        MenuOption savedMenuOption = menuOptionRepository.save(menuOption);

        List<MenuOptionDetail> details = dto.optionDetails().stream()
                .map(detailDto -> MenuOptionDetail.create(savedMenuOption.getId(),
                        detailDto.name(), detailDto.price()))
                .toList();
        List<MenuOptionDetail> savedDetails = menuOptionDetailRepository.saveAll(details);

        eventPublisher.publishEvent(MenuOptionCreateEvent.of(savedMenuOption, savedDetails));
        return savedMenuOption.getUuid();
    }

    @Transactional
    public void updateMenuOption(final MenuOptionUpdateDto dto) {
        MenuOption menuOption = menuOptionRepository.findByUuid(dto.uuid())
                .orElseThrow(() -> new MenuOptionNotFoundException(dto.uuid()));

        menuOption.update(dto);

        List<MenuOptionDetail> existingDetails = menuOptionDetailRepository
                .findByMenuOptionId(menuOption.getId());
        Map<String, MenuOptionDetail> existingDetailsMap = existingDetails.stream()
                .collect(Collectors.toMap(MenuOptionDetail::getUuid, Function.identity()));

        List<MenuOptionDetail> toSave = new ArrayList<>();
        List<String> processedUuids = new ArrayList<>();

        for (MenuOptionUpdateDto.OptionDetailDto detailDto : dto.optionDetails()) {
            if (detailDto.uuid() != null && existingDetailsMap.containsKey(
                    detailDto.uuid())) {
                // 존재하는 옵션들은 업데이트
                MenuOptionDetail existingDetail = existingDetailsMap.get(detailDto.uuid());
                existingDetail.update(detailDto.name(), detailDto.price());
                toSave.add(existingDetail);
            } else {
                // 새로운 옵션들은 List에 저장
                toSave.add(MenuOptionDetail.create(menuOption.getId(),
                        detailDto.name(), detailDto.price()));
            }
            processedUuids.add(detailDto.uuid());
        }

        // 업데이트에 없는 옵션들은 삭제
        List<MenuOptionDetail> toDelete = existingDetails.stream()
                .filter(detail -> !processedUuids.contains(detail.getUuid()))
                .collect(Collectors.toList());

        menuOptionDetailRepository.deleteAll(toDelete);
        List<MenuOptionDetail> savedDetails = menuOptionDetailRepository.saveAll(toSave);

        eventPublisher.publishEvent(MenuOptionUpdateEvent.of(menuOption, savedDetails));
    }

    @Transactional
    public void deleteMenuOption(final String uuid) {
        MenuOption menuOption = menuOptionRepository.findByUuid(uuid)
                .orElseThrow(() -> new MenuOptionNotFoundException(uuid));
        menuOptionDetailRepository.deleteByMenuOptionId(menuOption.getId());
        menuOptionRepository.delete(menuOption);
        eventPublisher.publishEvent(MenuOptionDeleteEvent.from(menuOption.getId()));
    }
}
