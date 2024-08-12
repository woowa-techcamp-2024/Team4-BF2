package woowa.team4.bff.menu.category.command;

public record MenuCategoryCreateCommand(String restaurantUuid,
                                        String name,
                                        String description) {

}
