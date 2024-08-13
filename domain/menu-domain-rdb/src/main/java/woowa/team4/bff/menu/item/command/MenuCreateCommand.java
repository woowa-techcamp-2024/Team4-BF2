package woowa.team4.bff.menu.item.command;

import lombok.Builder;

@Builder
public record MenuCreateCommand(String menuCategoryUuid,
                                String name,
                                String description,
                                long price) {

}
