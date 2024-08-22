package woowa.team4.bff.exposure.controller;

import static woowa.team4.bff.common.utils.ApiUtils.*;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.common.utils.ApiUtils.*;
import woowa.team4.bff.domain.ExposureRestaurantSummary;
import woowa.team4.bff.exposure.command.SearchCommand;
import woowa.team4.bff.exposure.service.RestaurantExposureListService;

@RequestMapping("/api/v1/restaurant-summary")
@RestController
@RequiredArgsConstructor
public class RestaurantSummaryApiController {

    private final RestaurantExposureListService restaurantExposureListService;

    @GetMapping("/cache")
    public ApiResult<List<ExposureRestaurantSummary>> getRestaurantSummaries(
            @RequestParam("keyword") String keyword,
            @RequestParam("deliveryLocation") String deliveryLocation,
            @RequestParam("pageNumber") Integer pageNumber
    ) {
        List<ExposureRestaurantSummary> response = restaurantExposureListService.search(
                new SearchCommand(keyword, deliveryLocation, pageNumber));
        return success(response);
    }
}
