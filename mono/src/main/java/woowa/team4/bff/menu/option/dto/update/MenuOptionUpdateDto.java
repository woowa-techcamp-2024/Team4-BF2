package woowa.team4.bff.menu.option.dto.update;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;

@Builder
public record MenuOptionUpdateDto(String uuid,
                                  String name,
                                  String description,
                                  List<OptionDetailDto> optionDetails) {

    public record OptionDetailDto(String uuid,
                                  String name,
                                  BigDecimal price) {

    }
}
