package woowa.team4.bff.menu.item.controller.create;

import static woowa.team4.bff.menu.utils.constants.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import woowa.team4.bff.menu.item.command.MenuCreateCommand;

public record MenuCreateRequest(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull @DecimalMin(value = MENU_MIN_PRICE) BigDecimal price) {

    public MenuCreateCommand toCommand(final String menuCategoryUuid) {
        return MenuCreateCommand.builder()
                .menuCategoryUuid(menuCategoryUuid)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
