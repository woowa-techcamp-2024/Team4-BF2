package woowa.team4.bff.menu.category.controller;

import static woowa.team4.bff.common.utils.ApiUtils.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.menu.category.dto.create.MenuCategoryCreateDto;
import woowa.team4.bff.menu.category.dto.create.MenuCategoryCreateRequest;
import woowa.team4.bff.menu.category.dto.create.MenuCategoryCreateResponse;
import woowa.team4.bff.menu.category.dto.update.MenuCategoryUpdateDto;
import woowa.team4.bff.menu.category.dto.update.MenuCategoryUpdateRequest;
import woowa.team4.bff.menu.category.dto.update.MenuCategoryUpdateResponse;
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
        MenuCategoryCreateDto menuCategoryCreateDto = menuCategoryCreateRequest
                .toDto(restaurantUuid);
        String menuCategoryUuid = menuCategoryService.createMenuCategory(menuCategoryCreateDto);
        return success(new MenuCategoryCreateResponse(menuCategoryUuid));
    }

    @PutMapping("/{menuCategoryUuid}")
    public ApiResult<MenuCategoryUpdateResponse> updateCategory(
            @PathVariable final String menuCategoryUuid,
            @Valid @RequestBody final MenuCategoryUpdateRequest menuCategoryUpdateRequest) {
        MenuCategoryUpdateDto menuCategoryUpdateDto = menuCategoryUpdateRequest
                .toDto(menuCategoryUuid);
        MenuCategoryUpdateDto updatedMenuCategoryDto = menuCategoryService
                .updateMenuCategory(menuCategoryUpdateDto);
        return success(MenuCategoryUpdateResponse.from(updatedMenuCategoryDto));
    }
}
