package woowa.team4.bff.menu.item.dto.create;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuCreateDto {

    private final String menuCategoryUuid;
    private final String name;
    private final String description;
    private final BigDecimal price;
}
