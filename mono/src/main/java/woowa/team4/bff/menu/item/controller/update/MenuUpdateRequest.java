package woowa.team4.bff.menu.item.controller.update;

import static woowa.team4.bff.menu.utils.Constants.*;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import woowa.team4.bff.menu.item.command.MenuUpdateCommand;

public record MenuUpdateRequest(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull @Min(MENU_MIN_PRICE) long price) {

    public MenuUpdateCommand toCommand(final String menuUuid) {
        return MenuUpdateCommand.builder()
                .uuid(menuUuid)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
