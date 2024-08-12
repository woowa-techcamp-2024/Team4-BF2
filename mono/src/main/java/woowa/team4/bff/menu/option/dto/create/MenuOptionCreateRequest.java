package woowa.team4.bff.menu.option.dto.create;

import jakarta.validation.constraints.NotBlank;
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
