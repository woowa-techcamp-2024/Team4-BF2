package woowa.team4.bff.menu.option.command;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record MenuOptionUpdateCommand(String uuid,
                                      String name,
                                      String description,
                                      List<OptionDetailCommand> optionDetails) {

    public record OptionDetailCommand(String uuid,
                                      String name,
                                      BigDecimal price) {

    }
}
