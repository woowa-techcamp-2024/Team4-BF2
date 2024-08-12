package woowa.team4.bff.menu.item.dto.update;

import java.math.BigDecimal;
import lombok.Builder;
import woowa.team4.bff.menu.item.entity.Menu;

@Builder
public record MenuUpdateDto(String uuid, String name, String description, BigDecimal price) {

    public static MenuUpdateDto from(final Menu menu) {
        return new MenuUpdateDto(menu.getUuid(), menu.getName(),
                menu.getDescription(), menu.getPrice());
    }
}
