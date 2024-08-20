package woowa.team4.bff.exposure.controller;

import static woowa.team4.bff.common.utils.ApiUtils.success;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.common.utils.ApiUtils.ApiResult;
import woowa.team4.bff.domain.RestaurantSummary;
import woowa.team4.bff.exposure.command.SearchCommand;
import woowa.team4.bff.exposure.controller.get.GetRestaurantSummaryRequest;
import woowa.team4.bff.exposure.service.RestaurantExposureListService;

@RequestMapping("/api/v1/restaurant-summary")
@RestController
@RequiredArgsConstructor
public class RestaurantSummaryApiController {
    private final RestaurantExposureListService restaurantExposureListService;

    @GetMapping("/cache")
    public ApiResult<List<RestaurantSummary>> getRestaurantSummaries(
            @RequestBody GetRestaurantSummaryRequest request
    ){
        List<RestaurantSummary> response = restaurantExposureListService.search(new SearchCommand(request.keyword(), request.deliveryLocation()));
        return success(response);
    }

    @GetMapping("/no-cache")
    public ApiResult<List<RestaurantSummary>> getRestaurantSummariesNoCache(
            @RequestBody GetRestaurantSummaryRequest request
            ){
        List<RestaurantSummary> response = restaurantExposureListService.searchNoCache(new SearchCommand(request.keyword(), request.deliveryLocation()));
        return success(response);
    }
}
