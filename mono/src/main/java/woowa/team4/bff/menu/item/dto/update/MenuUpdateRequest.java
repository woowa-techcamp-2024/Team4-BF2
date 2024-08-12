package woowa.team4.bff.menu.item.dto.update;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record MenuUpdateRequest(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull @DecimalMin(value = "0.0") BigDecimal price) {

    public MenuUpdateDto toDto(final String menuUuid) {
        return MenuUpdateDto.builder()
                .uuid(menuUuid)
                .name(name)
                .description(description)
                .price(price)
                .build();
    }
}
