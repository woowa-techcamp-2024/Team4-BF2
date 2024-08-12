package woowa.team4.bff.menu.item.dto.update;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import woowa.team4.bff.menu.item.entity.Menu;

@Getter
@Builder
public class MenuUpdateDto {

    private final String uuid;
    private final String name;
    private final String description;
    private final BigDecimal price;

    public static MenuUpdateDto from(final Menu menu) {
        return new MenuUpdateDto(menu.getUuid(), menu.getName(),
                menu.getDescription(), menu.getPrice());
    }
}
