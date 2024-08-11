package woowa.team4.bff.menu.item.dto.create;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuCreateRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotNull
    @DecimalMin(value = "0.0")
    private BigDecimal price;

    public MenuCreateDto toDto(final String menuCategoryUuid) {
        return MenuCreateDto.builder()
                .menuCategoryUuid(menuCategoryUuid)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
