package woowa.team4.bff.menu.category.controller.update;

public record MenuCategoryUpdateResponse(String name,
                                         String description) {

    public static MenuCategoryUpdateResponse from(
            final MenuCategoryUpdateDto menuCategoryUpdateDto) {
        return new MenuCategoryUpdateResponse(menuCategoryUpdateDto.name(),
                menuCategoryUpdateDto.description());
    }
}
