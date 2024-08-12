package woowa.team4.bff.menu.item.dto.create;

import static woowa.team4.bff.menu.utils.constants.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MenuCreateRequest(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull @DecimalMin(value = MENU_MIN_PRICE) BigDecimal price) {

    public MenuCreateDto toDto(final String menuCategoryUuid) {
        return MenuCreateDto.builder()
                .menuCategoryUuid(menuCategoryUuid)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
