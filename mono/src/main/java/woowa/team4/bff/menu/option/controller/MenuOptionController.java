package woowa.team4.bff.menu.option.controller;

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
import woowa.team4.bff.menu.option.dto.create.MenuOptionCreateRequest;
import woowa.team4.bff.menu.option.dto.create.MenuOptionCreateResponse;
import woowa.team4.bff.menu.option.service.MenuOptionService;

@RestController
@RequestMapping("/menu/option")
@RequiredArgsConstructor
public class MenuOptionController {

    private final MenuOptionService menuOptionService;

    @PostMapping("/{menuUuid}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<MenuOptionCreateResponse> createMenuOption(
            @PathVariable String menuUuid,
            @Valid @RequestBody MenuOptionCreateRequest request) {
        String menuOptionUuid = menuOptionService.createMenuOption(request.toDto(menuUuid));
        return success(new MenuOptionCreateResponse(menuOptionUuid));
    }
}
