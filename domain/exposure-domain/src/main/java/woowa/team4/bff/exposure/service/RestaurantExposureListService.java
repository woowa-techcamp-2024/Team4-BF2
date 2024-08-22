package woowa.team4.bff.exposure.service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import woowa.team4.bff.api.client.advertisement.response.AdvertisementResponse;
import woowa.team4.bff.api.client.coupon.response.CouponResponse;
import woowa.team4.bff.api.client.delivery.response.DeliveryTimeResponse;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.exposure.caller.AsyncExternalApiCaller;
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
    private final AsyncExternalApiCaller asyncExternalApiCaller;

    public List<RestaurantSummary> search(SearchCommand command) {
        List<Long> restaurantIds = searchService.findIdsByKeywordAndDeliveryLocation(
                command.keyword(), command.deliveryLocation(), command.pageNumber());
        // 동기식 외부 API 호출
        searchSynchronously(restaurantIds, command.keyword());

        // 비동기식 외부 API 호출
        searchAsynchronously(restaurantIds, command.keyword())
                .thenRun(() -> log.info("MVC Asynchronous call completed"))
                .exceptionally(ex -> {
                    log.error("An error occurred: {}", ex.getMessage());
                    return null;
                });

        // 비동기식 외부 API 호출 (WebFlux)
        searchAsynchronouslyWebFlux(restaurantIds, command.keyword())
                .doOnSuccess(v -> log.info("WebFlux Asynchronous call completed"))
                .doOnError(ex -> log.error("An error occurred: {}", ex.getMessage()))
                .subscribe();

        return cacheService.findByRestaurantIds(restaurantIds);
    }

    /**
     * 동기 방식으로 외부 API 호출
     *
     * @param restaurantIds
     */
    public void searchSynchronously(List<Long> restaurantIds, String keyword) {
        // 배달 외부 API 요청
        List<DeliveryTimeResponse> deliveryTimeResponse = syncExternalApiCaller
                .getDeliveryTime(restaurantIds);
        // 쿠폰 외부 API 요청
        List<CouponResponse> couponResponse = syncExternalApiCaller
                .getCoupon(restaurantIds);
        // 광고 외부 API 요청
        List<AdvertisementResponse> advertisementResponses = syncExternalApiCaller
                .getAdvertisement(restaurantIds, keyword);
    }

    /**
     * 비동기 방식으로 외부 API 호출
     *
     * @param restaurantIds
     */
    public CompletableFuture<Void> searchAsynchronously(List<Long> restaurantIds, String keyword) {
        // 배달 외부 API 요청
        CompletableFuture<List<DeliveryTimeResponse>> deliveryFuture = asyncExternalApiCaller
                .getDeliveryTime(restaurantIds)
                .thenApply(response -> {
                    // 배달 시간 응답 처리
                    log.info("Mvc Delivery time received");
                    return response;
                });
        // 쿠폰 외부 API 요청
        CompletableFuture<List<CouponResponse>> couponFuture = asyncExternalApiCaller
                .getCoupon(restaurantIds)
                .thenApply(response -> {
                    // 배달 시간 응답 처리
                    log.info("Mvc Coupon received");
                    return response;
                });
        // 광고 외부 API 요청
        CompletableFuture<List<AdvertisementResponse>> advertisementFuture = asyncExternalApiCaller
                .getAdvertisement(restaurantIds, keyword)
                .thenApply(response -> {
                    // 배달 시간 응답 처리
                    log.info("Mvc Advertisement received");
                    return response;
                });

        // 모든 결과가 완료된 후
        return CompletableFuture.allOf(deliveryFuture, couponFuture, advertisementFuture)
                .thenApply(v -> {
                    // 모든 응답이 도착한 후 실행될 로직
                    List<DeliveryTimeResponse> deliveryResponse = deliveryFuture.join();
                    List<CouponResponse> couponResponse = couponFuture.join();
                    List<AdvertisementResponse> advertisementResponse = advertisementFuture.join();
                    return null;
                });
    }

    /**
     * 비동기 방식으로 외부 API 호출 (WebFlux)
     *
     * @param restaurantIds
     */
    public Mono<Void> searchAsynchronouslyWebFlux(List<Long> restaurantIds, String keyword) {
        // 배달 외부 API 요청
        Mono<List<DeliveryTimeResponse>> deliveryMono = asyncExternalApiCaller
                .getDeliveryTimeWebFlux(restaurantIds)
                .doOnNext(response -> log.info("WebFlux Delivery time received"));

        // 쿠폰 외부 API 요청
        Mono<List<CouponResponse>> couponMono = asyncExternalApiCaller
                .getCouponWebFlux(restaurantIds)
                .doOnNext(response -> log.info("WebFlux Coupon received"));

        // 광고 외부 API 요청
        Mono<List<AdvertisementResponse>> advetisementMono = asyncExternalApiCaller
                .getAdvertisementWebFlux(restaurantIds, keyword)
                .doOnNext(response -> log.info("WebFlux Advertisement received"));

        // 모든 Mono를 결합
        return Mono.zip(deliveryMono, couponMono, advetisementMono)
                .flatMap(tuple -> {
                    List<DeliveryTimeResponse> deliveryResponse = tuple.getT1();
                    List<CouponResponse> couponResponse = tuple.getT2();
                    List<AdvertisementResponse> advertisementResponse = tuple.getT3();

                    // 여기서 모든 응답을 조합하거나 추가 처리를 수행
                    log.info("WebFlux All responses received and processed");

                    // 필요한 처리를 수행한 후 Mono<Void>를 반환
                    return Mono.empty();
                })
                .then(); // Mono<Void>로 변환
    }

    // ToDo: 실험 후 제거
    public List<RestaurantSummary> searchNoCache(SearchCommand command) {
        return searchService.findRestaurantSummaryByKeywordAndDeliveryLocation(command.keyword(),
                command.deliveryLocation(), command.pageNumber());
    }
}
