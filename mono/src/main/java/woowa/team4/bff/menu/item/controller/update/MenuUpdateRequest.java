package woowa.team4.bff.menu.item.controller.update;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import woowa.team4.bff.menu.item.command.MenuUpdateCommand;

public record MenuUpdateRequest(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull @DecimalMin(value = "0.0") BigDecimal price) {

    public MenuUpdateCommand toCommand(final String menuUuid) {
        return MenuUpdateCommand.builder()
                .uuid(menuUuid)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
