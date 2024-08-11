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

    private String menuId;

    public void update(String menuName){
        this.menuName = menuName;
    }


    @Override
    public String toString() {
        return "MenuSearch{" +
                "id='" + id + '\'' +
                ", menuName='" + menuName + '\'' +
                ", menuId='" + menuId + '\'' +
                '}';
    }
}
