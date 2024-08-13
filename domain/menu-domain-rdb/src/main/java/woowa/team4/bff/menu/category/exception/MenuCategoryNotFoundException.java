package woowa.team4.bff.menu.category.exception;

public class MenuCategoryNotFoundException extends RuntimeException {

    public MenuCategoryNotFoundException(final String uuid) {
        super("Menu category not found with UUID: " + uuid);
    }
}
