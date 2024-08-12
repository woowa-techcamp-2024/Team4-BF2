package woowa.team4.bff.menu.item.dto.update;

import java.math.BigDecimal;

public record MenuUpdateResponse(String name, String description, BigDecimal price) {

    public static MenuUpdateResponse from(final MenuUpdateDto menuUpdateDto) {
        return new MenuUpdateResponse(menuUpdateDto.name(),
                menuUpdateDto.description(),
                menuUpdateDto.price());
    }
}
