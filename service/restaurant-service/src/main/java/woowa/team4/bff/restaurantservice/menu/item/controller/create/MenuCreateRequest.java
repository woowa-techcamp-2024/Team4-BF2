package woowa.team4.bff.restaurantservice.menu.item.controller.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import woowa.team4.bff.menu.item.command.MenuCreateCommand;

public record MenuCreateRequest(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull @Min(value = 0) long price) {

    public MenuCreateCommand toCommand(final String menuCategoryUuid) {
        return MenuCreateCommand.builder()
                .menuCategoryUuid(menuCategoryUuid)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
