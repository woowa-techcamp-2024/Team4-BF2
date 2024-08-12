package woowa.team4.bff.menu.item.dto.create;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record MenuCreateDto(String menuCategoryUuid,
                            String name,
                            String description,
                            BigDecimal price) {

}
