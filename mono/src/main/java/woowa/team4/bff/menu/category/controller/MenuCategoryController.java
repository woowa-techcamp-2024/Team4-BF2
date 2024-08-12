package woowa.team4.bff.menu.category.controller;

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
import woowa.team4.bff.menu.category.command.MenuCategoryCreateCommand;
import woowa.team4.bff.menu.category.command.MenuCategoryUpdateCommand;
import woowa.team4.bff.menu.category.controller.create.MenuCategoryCreateRequest;
import woowa.team4.bff.menu.category.controller.create.MenuCategoryCreateResponse;
import woowa.team4.bff.menu.category.controller.update.MenuCategoryUpdateRequest;
import woowa.team4.bff.menu.category.controller.update.MenuCategoryUpdateResponse;
import woowa.team4.bff.menu.category.service.MenuCategoryService;

@RestController
@RequestMapping("/menu/category")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @PostMapping("/{restaurantUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MenuCategoryCreateResponse> createCategory(
            @PathVariable final String restaurantUuid,
            @Valid @RequestBody final MenuCategoryCreateRequest menuCategoryCreateRequest) {
        MenuCategoryCreateCommand menuCategoryCreateCommand = menuCategoryCreateRequest
                .toDto(restaurantUuid);
        String menuCategoryUuid = menuCategoryService.createMenuCategory(menuCategoryCreateCommand);
        return success(new MenuCategoryCreateResponse(menuCategoryUuid));
    }

    @PutMapping("/{menuCategoryUuid}")
    public ApiResult<MenuCategoryUpdateResponse> updateCategory(
            @PathVariable final String menuCategoryUuid,
            @Valid @RequestBody final MenuCategoryUpdateRequest menuCategoryUpdateRequest) {
        MenuCategoryUpdateCommand menuCategoryUpdateCommand = menuCategoryUpdateRequest
                .toDto(menuCategoryUuid);
        MenuCategoryUpdateCommand updatedMenuCategoryDto = menuCategoryService
                .updateMenuCategory(menuCategoryUpdateCommand);
        return success(MenuCategoryUpdateResponse.from(updatedMenuCategoryDto));
    }

    @DeleteMapping("/{menuCategoryUuid}")
    public ApiResult<Boolean> deleteCategory(
            @PathVariable final String menuCategoryUuid) {
        return success(menuCategoryService.deleteMenuCategory(menuCategoryUuid));
    }
}
