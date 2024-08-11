package woowa.team4.bff.menu.item.dto.update;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuUpdateResponse {

    private final String name;
    private final String description;
    private final BigDecimal price;

    public static MenuUpdateResponse from(final MenuUpdateDto menuUpdateDto) {
        return new MenuUpdateResponse(menuUpdateDto.getName(),
                menuUpdateDto.getDescription(),
                menuUpdateDto.getPrice());
    }
}
