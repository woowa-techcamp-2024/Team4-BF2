package woowa.team4.bff.menu.category.controller;

import static woowa.team4.bff.common.utils.ApiUtils.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.menu.category.dto.MenuCategoryCreateRequest;
import woowa.team4.bff.menu.category.dto.MenuCategoryCreateResponse;
import woowa.team4.bff.menu.category.dto.MenuCategoryDto;
import woowa.team4.bff.menu.category.service.MenuCategoryService;

@RestController
@RequestMapping("/menu/category")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @PostMapping("/{restaurantUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MenuCategoryCreateResponse> createCategory(
            @PathVariable String restaurantUuid,
            @Valid @RequestBody MenuCategoryCreateRequest menuCategoryCreateRequest) {
        MenuCategoryDto menuCategoryDto = menuCategoryCreateRequest.toMenuCategoryDto(
                restaurantUuid);
        String menuCategoryUuid = menuCategoryService.createMenuCategory(menuCategoryDto);
        return success(new MenuCategoryCreateResponse(menuCategoryUuid));
    }
}
