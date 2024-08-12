package woowa.team4.bff.menu.option.dto.update;

import java.math.BigDecimal;
import java.util.List;

public record MenuOptionUpdateRequest(String name,
                                      String description,
                                      List<OptionDetailRequest> optionDetails) {

    public record OptionDetailRequest(String uuid,
                                      String name,
                                      BigDecimal price) {

    }

    public MenuOptionUpdateDto toDto(final String menuOptionUuid) {
        return MenuOptionUpdateDto.builder()
                .uuid(menuOptionUuid)
                .name(name)
                .description(description)
                .optionDetails(optionDetails.stream()
                        .map(detail -> new MenuOptionUpdateDto.OptionDetailDto(detail.uuid(),
                                detail.name(), detail.price()))
                        .toList())
                .build();
    }
}
