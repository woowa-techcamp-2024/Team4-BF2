package woowa.team4.bff.menu.item.command;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record MenuCreateCommand(String menuCategoryUuid,
                                String name,
                                String description,
                                BigDecimal price) {

}
