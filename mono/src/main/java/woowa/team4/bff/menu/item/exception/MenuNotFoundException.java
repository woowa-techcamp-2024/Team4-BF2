package woowa.team4.bff.menu.item.exception;

public class MenuNotFoundException extends RuntimeException {

    public MenuNotFoundException(final String uuid) {
        super("Menu not found with UUID: " + uuid);
    }
}
