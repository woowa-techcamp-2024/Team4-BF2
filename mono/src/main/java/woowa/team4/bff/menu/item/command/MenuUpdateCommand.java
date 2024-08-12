package woowa.team4.bff.menu.item.command;

import java.math.BigDecimal;
import lombok.Builder;
import woowa.team4.bff.menu.item.entity.Menu;

@Builder
public record MenuUpdateCommand(String uuid, String name, String description, BigDecimal price) {

    public static MenuUpdateCommand from(final Menu menu) {
        return new MenuUpdateCommand(menu.getUuid(), menu.getName(),
                menu.getDescription(), menu.getPrice());
    }
}
