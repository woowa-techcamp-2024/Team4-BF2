package woowa.team4.bff.menu.option.dto.create;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record MenuOptionCreateDto(String menuUuid,
                                  String name,
                                  String description,
                                  List<OptionDetailDto> optionDetails) {

    public record OptionDetailDto(String name, BigDecimal price) {

    }
}
