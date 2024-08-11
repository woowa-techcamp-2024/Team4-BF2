package woowa.team4.bff.search.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MenuSearch {
    private String id;

    private String menuName;

    private Long menuId;
    private Long restaurantId;

    public void update(String menuName){
        this.menuName = menuName;
    }
}
