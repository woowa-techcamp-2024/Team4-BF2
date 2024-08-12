package woowa.team4.bff.menu.option.controller.update;

import java.math.BigDecimal;
import java.util.List;
import woowa.team4.bff.menu.option.command.MenuOptionUpdateCommand;
import woowa.team4.bff.menu.option.command.MenuOptionUpdateCommand.OptionDetailCommand;

public record MenuOptionUpdateRequest(String name,
                                      String description,
                                      List<OptionDetailRequest> optionDetails) {

    public record OptionDetailRequest(String uuid,
                                      String name,
                                      BigDecimal price) {

    }

    public MenuOptionUpdateCommand toCommand(final String menuOptionUuid) {
        return MenuOptionUpdateCommand.builder()
                .uuid(menuOptionUuid)
                .name(name)
                .description(description)
                .optionDetails(optionDetails.stream()
                        .map(detail -> new OptionDetailCommand(detail.uuid(),
                                detail.name(), detail.price()))
                        .toList())
                .build();
    }
}
