package woowa.team4.bff.exposure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.advertisement.response.AdvertisementResponse;
import woowa.team4.bff.api.client.coupon.response.CouponResponse;
import woowa.team4.bff.api.client.delivery.response.DeliveryTimeResponse;
import woowa.team4.bff.domain.ExposureRestaurantSummary;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.event.cache.DeliveryLocationAndKeywordCreateEvent;
import woowa.team4.bff.exposure.command.SearchCommand;
import woowa.team4.bff.exposure.external.caller.AsyncExternalApiCaller;
import woowa.team4.bff.exposure.external.result.ExternalApiResult;
import woowa.team4.bff.interfaces.CacheService;
import woowa.team4.bff.interfaces.SearchService;
import woowa.team4.bff.publisher.EventPublisher;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantExposureListService {

    private static final long DEFAULT_PAGE_SIZE = 25L;

    private final SearchService searchService;
    private final CacheService cacheService;
    private final EventPublisher eventPublisher;
    private final AsyncExternalApiCaller asyncExternalApiCaller;

    public List<ExposureRestaurantSummary> search(SearchCommand command) {
        List<Long> restaurantIds = cacheService.findIdsByKeywordAndDeliveryLocation(
                command.keyword(),
                command.deliveryLocation());
        if (restaurantIds == null) {
            restaurantIds = searchService.findIdsByKeywordAndDeliveryLocation(command.keyword(),
                    command.deliveryLocation(), command.pageNumber());
            eventPublisher.publish(new DeliveryLocationAndKeywordCreateEvent(command.keyword(),
                    command.deliveryLocation(), restaurantIds));
        }

        //1. 동기식 캐시 서비스 호출
        List<RestaurantSummary> cachedSummaries = cacheService.findByRestaurantIds(restaurantIds);

        //2. 비동기식 외부 API 호출
        List<ExternalApiResult> externalApiResults = getExternalResult(restaurantIds,
                command.keyword());

        return mergeSummariesWithExternalResults(cachedSummaries, externalApiResults)
                .stream()
                .skip(DEFAULT_PAGE_SIZE * command.pageNumber())
                .limit(DEFAULT_PAGE_SIZE)
                .toList();
    }

    public List<ExternalApiResult> getExternalResult(
            final List<Long> restaurantIds, final String keyword) {
        return searchAsynchronouslyWebFlux(restaurantIds, keyword).block();
    }

    /**
     * 비동기 방식으로 외부 API 호출 (WebFlux)
     *
     * @param restaurantIds
     */
    public Mono<List<ExternalApiResult>> searchAsynchronouslyWebFlux(List<Long> restaurantIds,
            String keyword) {
        // 배달 외부 API 요청
        Mono<List<DeliveryTimeResponse>> deliveryMono = asyncExternalApiCaller
                .getDeliveryTimeWebFlux(restaurantIds)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.just(restaurantIds.stream()
                                .map(id -> {
                                    DeliveryTimeResponse deliveryTimeResponse = new DeliveryTimeResponse();
                                    deliveryTimeResponse.setRestaurantId(id);
                                    return deliveryTimeResponse;
                                })
                                .collect(Collectors.toList()))
                ))
                .doOnNext(response -> log.info("WebFlux Delivery time received"));

        // 쿠폰 외부 API 요청
        Mono<List<CouponResponse>> couponMono = asyncExternalApiCaller
                .getCouponWebFlux(restaurantIds)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.just(restaurantIds.stream()
                                .map(id -> {
                                    CouponResponse couponResponse = new CouponResponse();
                                    couponResponse.setRestaurantId(id);
                                    return couponResponse;
                                })
                                .collect(Collectors.toList()))
                ))
                .doOnNext(response -> log.info("WebFlux Coupon received"));

        // 광고 외부 API 요청
        Mono<List<AdvertisementResponse>> advertisementMono = asyncExternalApiCaller
                .getAdvertisementWebFlux(restaurantIds, keyword)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.just(restaurantIds.stream()
                                .map(id -> {
                                    AdvertisementResponse advertisementResponse = new AdvertisementResponse();
                                    advertisementResponse.setRestaurantId(id);
                                    return advertisementResponse;
                                })
                                .collect(Collectors.toList()))
                ))
                .doOnNext(response -> log.info("WebFlux Advertisement received"));

        // 모든 Mono를 결합
        return Mono.zip(deliveryMono, couponMono, advertisementMono)
                .map(tuple -> {
                    List<DeliveryTimeResponse> deliveryResponses = tuple.getT1();
                    List<CouponResponse> couponResponses = tuple.getT2();
                    List<AdvertisementResponse> advertisementResponses = tuple.getT3();

                    // 응답들을 restaurantId를 키로 하는 Map으로 변환
                    Map<Long, DeliveryTimeResponse> deliveryMap = deliveryResponses.stream()
                            .collect(toMap(DeliveryTimeResponse::getRestaurantId,
                                    r -> r));
                    Map<Long, CouponResponse> couponMap = couponResponses.stream()
                            .collect(toMap(CouponResponse::getRestaurantId, r -> r));
                    Map<Long, AdvertisementResponse> adMap = advertisementResponses.stream()
                            .collect(toMap(AdvertisementResponse::getRestaurantId,
                                    r -> r));

                    // ExternalApiResult 리스트 생성
                    return restaurantIds.stream()
                            .map(id -> ExternalApiResult.builder()
                                    .restaurantId(id)
                                    .adRank(adMap.get(id).getAdRank())
                                    .hasAdvertisement(adMap.get(id).isHasAdvertisement())
                                    .hasCoupon(couponMap.get(id).isHasCoupon())
                                    .couponName(couponMap.get(id).getCouponName())
                                    .min(deliveryMap.get(id).getMin())
                                    .max(deliveryMap.get(id).getMax())
                                    .build()

                            ).toList();
                })
                .doOnNext(results -> log.info("WebFlux All responses received and processed"));
    }

    private List<ExposureRestaurantSummary> mergeSummariesWithExternalResults(
            List<RestaurantSummary> summaries, List<ExternalApiResult> externalResults) {
        Map<Long, ExternalApiResult> resultMap = externalResults.stream()
                .collect(Collectors.toMap(ExternalApiResult::getRestaurantId, r -> r));

        return summaries.stream()
                .map(summary -> {
                    ExternalApiResult result = resultMap.get(summary.getId());
                    return updateSummaryWithExternalResult(summary, result);
                })
                .collect(toList());
    }

    private ExposureRestaurantSummary updateSummaryWithExternalResult(RestaurantSummary summary,
            ExternalApiResult result) {
        return ExposureRestaurantSummary.builder()
                .restaurantUuid(summary.getRestaurantUuid())
                .restaurantName(summary.getRestaurantName())
                .restaurantThumbnailUrl(summary.getRestaurantThumbnailUrl())
                .minimumOrderAmount(summary.getMinimumOrderAmount())
                .reviewCount(summary.getReviewCount())
                .rating(summary.getRating())
                .menus(summary.getMenus())
                .adRank(result.getAdRank())
                .hasAdvertisement(result.isHasAdvertisement())
                .hasCoupon(result.isHasCoupon())
                .couponName(result.getCouponName())
                .min(result.getMin())
                .max(result.getMax())
                .build();
    }
}
