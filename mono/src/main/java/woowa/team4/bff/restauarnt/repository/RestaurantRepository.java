package woowa.team4.bff.restauarnt.repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.restauarnt.domain.Restaurant;

@Repository
public class RestaurantRepository {
    public List<Restaurant> findAllByIds(List<String> restaurantIds, String deliveryLocaltion){
        // ToDo: Restaurant 필드 보고 select 쿼리 작성
        return null;
    }
}
