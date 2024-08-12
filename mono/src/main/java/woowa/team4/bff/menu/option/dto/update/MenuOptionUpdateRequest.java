package woowa.team4.bff.menu.option.dto.update;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuOptionUpdateRequest {

    private String name;
    private String description;
    private List<OptionDetailRequest> optionDetails;

    @Getter
    @Setter
    public static class OptionDetailRequest {

        private String uuid;
        private String name;
        private BigDecimal price;
    }

    public MenuOptionUpdateDto toDto(String menuOptionUuid) {
        return MenuOptionUpdateDto.builder()
                .uuid(menuOptionUuid)
                .name(name)
                .description(description)
                .optionDetails(optionDetails.stream()
                        .map(detail -> new MenuOptionUpdateDto.OptionDetailDto(detail.getUuid(),
                                detail.getName(), detail.getPrice()))
                        .toList())
                .build();
    }
}
