package woowa.team4.bff.exposure.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import woowa.team4.bff.api.client.coupon.response.CouponResponse;
import woowa.team4.bff.api.client.delivery.response.DeliveryTimeResponse;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.exposure.caller.SyncExternalApiCaller;
import woowa.team4.bff.exposure.command.SearchCommand;
import woowa.team4.bff.interfaces.CacheService;
import woowa.team4.bff.interfaces.SearchService;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantExposureListService {

    private final SearchService searchService;
    private final CacheService cacheService;
    private final SyncExternalApiCaller syncExternalApiCaller;

    public List<RestaurantSummary> search(SearchCommand command) {
        List<Long> restaurantIds = searchService.findIdsByKeywordAndDeliveryLocation(
                command.keyword(), command.deliveryLocation(), command.pageNumber());
        // 외부 API 호출
        searchSynchronously(restaurantIds);

        return cacheService.findByRestaurantIds(restaurantIds);
    }

    /**
     * 동기 방식으로 외부 API 호출
     *
     * @param restaurantIds
     */
    public void searchSynchronously(List<Long> restaurantIds) {
        // 배달 외부 API 요청
        List<DeliveryTimeResponse> deliveryTimeResponse = syncExternalApiCaller
                .getDeliveryTime(restaurantIds);
        // 가게 외부 API 요청

        // 쿠폰 외부 API 요청
        List<CouponResponse> couponResponse = syncExternalApiCaller
                .getCoupon(restaurantIds);
    }

    /**
     * 비동기 방식으로 외부 API 호출
     *
     * @param restaurantIds
     */
    public void searchAsynchronously(List<Long> restaurantIds) {

    }

    // ToDo: 실험 후 제거
    public List<RestaurantSummary> searchNoCache(SearchCommand command) {
        return searchService.findRestaurantSummaryByKeywordAndDeliveryLocation(command.keyword(),
                command.deliveryLocation(), command.pageNumber());
    }
}
