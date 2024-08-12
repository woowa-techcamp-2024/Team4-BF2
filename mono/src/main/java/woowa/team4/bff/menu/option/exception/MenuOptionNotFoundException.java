package woowa.team4.bff.menu.option.exception;

public class MenuOptionNotFoundException extends RuntimeException {

    public MenuOptionNotFoundException(final String uuid) {
        super("Menu Option not found with UUID: " + uuid);
    }
}
