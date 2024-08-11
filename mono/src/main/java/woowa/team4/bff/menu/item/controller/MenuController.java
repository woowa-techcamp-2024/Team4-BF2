package woowa.team4.bff.menu.item.controller;

import static woowa.team4.bff.common.utils.ApiUtils.*;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.menu.item.dto.create.MenuCreateDto;
import woowa.team4.bff.menu.item.dto.create.MenuCreateRequest;
import woowa.team4.bff.menu.item.dto.create.MenuCreateResponse;
import woowa.team4.bff.menu.item.service.MenuService;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/{menuCategoryUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MenuCreateResponse> createMenu(@PathVariable final String menuCategoryUuid,
            @RequestBody final MenuCreateRequest menuCreateRequest) {
        MenuCreateDto menuCreateDto = menuCreateRequest.toDto(menuCategoryUuid);
        String menuUuid = menuService.createMenu(menuCreateDto);
        return success(new MenuCreateResponse(menuUuid));
    }
}
