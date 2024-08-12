package woowa.team4.bff.menu.option.dto.create;

import static woowa.team4.bff.menu.utils.constants.*;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionCreateRequest {

    @NotBlank
    private String name;
    private String description;
    private List<OptionDetailRequest> optionDetails;

    @Getter
    @Setter
    public static class OptionDetailRequest {

        private String name;
        @NotNull
        @DecimalMin(value = MENU_OPTION_MIN_PRICE)
        private BigDecimal price;
    }

    public MenuOptionCreateDto toDto(String menuUuid) {
        return MenuOptionCreateDto.builder()
                .menuUuid(menuUuid)
                .name(name)
                .description(description)
                .optionDetails(optionDetails.stream()
                        .map(detail -> new MenuOptionCreateDto.OptionDetailDto(
                                detail.getName(),
                                detail.getPrice()))
                        .toList())
                .build();
    }
}
