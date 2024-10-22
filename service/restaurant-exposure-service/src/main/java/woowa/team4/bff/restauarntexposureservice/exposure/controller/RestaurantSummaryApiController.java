package woowa.team4.bff.restauarntexposureservice.exposure.controller;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import woowa.team4.bff.domain.ExposureRestaurantSummary;
import woowa.team4.bff.exposure.command.SearchCommand;
import woowa.team4.bff.exposure.service.RestaurantExposureListService;
import woowa.team4.bff.restauarntexposureservice.exposure.utils.ApiUtils;
import woowa.team4.bff.restauarntexposureservice.exposure.utils.ApiUtils.ApiResult;

@RequestMapping("/api/v1/restaurant-summary")
@RestController
@RequiredArgsConstructor
public class RestaurantSummaryApiController {

    private final RestaurantExposureListService restaurantExposureListService;

    @GetMapping("/cache")
    public Mono<ApiResult<List<ExposureRestaurantSummary>>> getRestaurantSummaries(
            @RequestParam("keyword") String keyword,
            @RequestParam("deliveryLocation") String deliveryLocation,
            @RequestParam("pageNumber") Integer pageNumber
    ) {
        return restaurantExposureListService.search(
                        new SearchCommand(keyword, deliveryLocation, pageNumber))
                .map(ApiUtils::success);
    }
}
