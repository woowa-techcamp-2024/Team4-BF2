package woowa.team4.bff.search.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.interfaces.SearchService;
import woowa.team4.bff.search.repository.RestaurantSummaryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchRdbService implements SearchService {

    private final RestaurantSummaryRepository restaurantSummaryRepository;

    @Override
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation, Integer pageNumber) {
        List<Long> res =  restaurantSummaryRepository.findIdsByKeywordAndDeliveryLocation(keyword, deliveryLocation, pageNumber);
        log.info("[findIdsByKeywordAndDeliveryLocation] " + res);
        return res;
    }
}
