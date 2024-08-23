package woowa.team4.bff.interfaces;

import java.util.List;

public interface SearchService {
    List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation, Integer pageNumber);
}
