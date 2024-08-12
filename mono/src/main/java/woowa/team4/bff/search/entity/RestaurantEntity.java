package woowa.team4.bff.search.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class RestaurantEntity {
    @Id
    @Generated
    private Long id;
    // ToDo: Common 으로 빼는가, 중복 코드를 사용 하는가
}
