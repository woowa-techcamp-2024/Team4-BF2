package woowa.team4.bff.menu.option.dto.create;

import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MenuOptionCreateDto {

    private String menuUuid;
    private String name;
    private String description;
    private List<OptionDetailDto> optionDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class OptionDetailDto {

        private String name;
        private BigDecimal price;
    }
}
