package woowa.team4.bff.exposure.service;

import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.advertisement.response.AdvertisementResponse;
import woowa.team4.bff.api.client.cache.caller.CacheApiCaller;
import woowa.team4.bff.api.client.cache.request.RankingRequest;
import woowa.team4.bff.api.client.cache.response.CacheResponse;
import woowa.team4.bff.api.client.caller.SearchApiCaller;
import woowa.team4.bff.api.client.coupon.response.CouponResponse;
import woowa.team4.bff.api.client.delivery.response.DeliveryTimeResponse;
import woowa.team4.bff.api.client.request.SearchRequest;
import woowa.team4.bff.api.client.response.SearchResponse;
import woowa.team4.bff.domain.ExposureRestaurantSummary;
import woowa.team4.bff.exposure.command.SearchCommand;
import woowa.team4.bff.exposure.external.caller.AsyncExternalApiCaller;
import woowa.team4.bff.exposure.external.result.ExternalApiResult;

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

    private final SearchApiCaller searchApiCaller;
    private final CacheApiCaller cacheApiCaller;
    private final AsyncExternalApiCaller asyncExternalApiCaller;

    public List<ExposureRestaurantSummary> search(SearchCommand command) {
        SearchResponse searchResponse = searchApiCaller.send(new SearchRequest(command.keyword(),
                command.deliveryLocation(), command.pageNumber()));
        List<Long> restaurantIds = searchResponse.getIds();
        log.info("[search] restaurantIds: {}", restaurantIds);
        // 비동기 호출
        cacheApiCaller.sendAsyncMono(new RankingRequest(command.keyword())).subscribe();
        List<ExternalApiResult> externalApiResults = getExternalResult(restaurantIds,
                command.keyword())
                .stream()
                .sorted(
                        Comparator.comparing(ExternalApiResult::getAdRank)
                                .thenComparing(ExternalApiResult::getMin)
                )
                .skip(DEFAULT_PAGE_SIZE * command.pageNumber())
                .limit(DEFAULT_PAGE_SIZE)
                .toList();

        return mergeSummariesWithExternalResults(externalApiResults);
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
        // 캐시 외부 API 요청
        Mono<List<CacheResponse>> restaurantSummaryMono = asyncExternalApiCaller
                .getCacheWebFlux(restaurantIds)
                .switchIfEmpty(Mono.defer(() ->
                        Mono.just(restaurantIds.stream()
                                .map(id -> {
                                    CacheResponse cacheResponse = new CacheResponse();
                                    cacheResponse.setRestaurantId(id);
                                    return cacheResponse;
                                })
                                .collect(Collectors.toList()))
                ))
                .doOnNext(response -> log.info("WebFlux Cache received"));

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
        return Mono.zip(restaurantSummaryMono, deliveryMono, couponMono, advertisementMono)
                .map(tuple -> {
                    List<CacheResponse> restaurantSummaryResponses = tuple.getT1();
                    List<DeliveryTimeResponse> deliveryResponses = tuple.getT2();
                    List<CouponResponse> couponResponses = tuple.getT3();
                    List<AdvertisementResponse> advertisementResponses = tuple.getT4();

                    // 응답들을 restaurantId를 키로 하는 Map으로 변환
                    Map<Long, CacheResponse> restaurantSummaryMap = restaurantSummaryResponses.stream()
                            .collect(toMap(CacheResponse::getRestaurantId,
                                    r -> r));
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
                                    .restaurantUuid(restaurantSummaryMap.get(id).getRestaurantUuid())
                                    .restaurantName(restaurantSummaryMap.get(id).getRestaurantName())
                                    .restaurantThumbnailUrl(restaurantSummaryMap.get(id).getRestaurantThumbnailUrl())
                                    .minimumOrderAmount(restaurantSummaryMap.get(id).getMinimumOrderAmount())
                                    .reviewCount(restaurantSummaryMap.get(id).getReviewCount())
                                    .rating(restaurantSummaryMap.get(id).getRating())
                                    .menus(restaurantSummaryMap.get(id).getMenus())
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
            List<ExternalApiResult> externalResults) {
        return externalResults.stream()
                .map(this::updateSummaryWithExternalResult
                )
                .collect(toList());
    }

    private ExposureRestaurantSummary updateSummaryWithExternalResult(
            ExternalApiResult result) {
        return ExposureRestaurantSummary.builder()
                .restaurantUuid(result.getRestaurantUuid())
                .restaurantName(result.getRestaurantName())
                .restaurantThumbnailUrl(result.getRestaurantThumbnailUrl())
                .minimumOrderAmount(result.getMinimumOrderAmount())
                .reviewCount(result.getReviewCount())
                .rating(result.getRating())
                .menus(result.getMenus())
                .adRank(result.getAdRank())
                .hasAdvertisement(result.isHasAdvertisement())
                .hasCoupon(result.isHasCoupon())
                .couponName(result.getCouponName())
                .min(result.getMin())
                .max(result.getMax())
                .build();
    }
}
