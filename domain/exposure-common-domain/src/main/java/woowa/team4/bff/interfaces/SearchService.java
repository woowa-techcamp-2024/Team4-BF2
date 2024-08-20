package woowa.team4.bff.interfaces;

import java.util.List;

public interface SearchService {
    List<Long> findByKeywordAndDeliveryLocation(String keyword, String deliveryLocation);
}
