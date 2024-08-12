package woowa.team4.bff.menu.option.dto.create;

import static woowa.team4.bff.menu.utils.constants.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public record MenuOptionCreateRequest(@NotBlank String name,
                                      String description,
                                      List<OptionDetailRequest> optionDetails) {

    public record OptionDetailRequest(String name,
                                      @NotNull @DecimalMin(value = MENU_OPTION_MIN_PRICE) BigDecimal price) {

    }

    public MenuOptionCreateDto toDto(String menuUuid) {
        return MenuOptionCreateDto.builder()
                .menuUuid(menuUuid)
                .name(name)
                .description(description)
                .optionDetails(optionDetails.stream()
                        .map(detail -> new MenuOptionCreateDto.OptionDetailDto(
                                detail.name(),
                                detail.price()))
                        .toList())
                .build();
    }
}
