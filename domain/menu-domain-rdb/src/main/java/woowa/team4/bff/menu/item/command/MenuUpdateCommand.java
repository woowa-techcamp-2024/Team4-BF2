package woowa.team4.bff.menu.item.command;

import lombok.Builder;

@Builder
public record MenuUpdateCommand(String uuid,
                                String name,
                                String description,
                                long price) {

}
