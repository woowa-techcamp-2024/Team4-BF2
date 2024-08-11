package woowa.team4.bff.menu.item.controller;

import static woowa.team4.bff.common.utils.ApiUtils.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.menu.item.dto.create.MenuCreateDto;
import woowa.team4.bff.menu.item.dto.create.MenuCreateRequest;
import woowa.team4.bff.menu.item.dto.create.MenuCreateResponse;
import woowa.team4.bff.menu.item.dto.update.MenuUpdateDto;
import woowa.team4.bff.menu.item.dto.update.MenuUpdateRequest;
import woowa.team4.bff.menu.item.dto.update.MenuUpdateResponse;
import woowa.team4.bff.menu.item.service.MenuService;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{menuCategoryUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MenuCreateResponse> createMenu(@PathVariable final String menuCategoryUuid,
            @Valid @RequestBody final MenuCreateRequest menuCreateRequest) {
        MenuCreateDto menuCreateDto = menuCreateRequest.toDto(menuCategoryUuid);
        String menuUuid = menuService.createMenu(menuCreateDto);
        return success(new MenuCreateResponse(menuUuid));
    }

    @PutMapping("/{menuUuid}")
    public ApiResult<MenuUpdateResponse> updateMenu(@PathVariable final String menuUuid,
            @Valid @RequestBody final MenuUpdateRequest menuUpdateRequest) {
        MenuUpdateDto menuUpdateDto = menuUpdateRequest.toDto(menuUuid);
        MenuUpdateDto updatedMenu = menuService.updateMenu(menuUpdateDto);
        return success(MenuUpdateResponse.from(updatedMenu));
    }

    @DeleteMapping("/{menuUuid}")
    public ApiResult<Boolean> deleteMenu(@PathVariable final String menuUuid) {
        return success(menuService.deleteMenu(menuUuid));
    }
}
