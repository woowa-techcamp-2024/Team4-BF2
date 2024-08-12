package woowa.team4.bff.menu.item.controller.update;

import java.math.BigDecimal;
import woowa.team4.bff.menu.item.command.MenuUpdateCommand;

public record MenuUpdateResponse(String name, String description, BigDecimal price) {

    public static MenuUpdateResponse from(final MenuUpdateCommand menuUpdateCommand) {
        return new MenuUpdateResponse(menuUpdateCommand.name(),
                menuUpdateCommand.description(),
                menuUpdateCommand.price());
    }
}
