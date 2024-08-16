package woowa.team4.bff.search.controller;

import static woowa.team4.bff.common.utils.ApiUtils.success;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.common.utils.ApiUtils.ApiResult;
import woowa.team4.bff.search.domain.RestaurantSearchResult;
import woowa.team4.bff.search.service.SearchService;
import woowa.team4.bff.search.command.SearchRestaurantCommand;

@RequestMapping("/api/v1/search")
@RestController
@RequiredArgsConstructor
public class SearchApiController {
    private final SearchService searchService;

//    @GetMapping("/es")
//    public ApiResult<List<RestaurantSearchResult>> searchRestaurants(
//            @RequestParam("keyword") String keyword,
//            @RequestParam("deliveryLocation") String deliveryLocation) {
//        List<RestaurantSearchResult> response = searchService.searchEs(
//                SearchRestaurantCommand.of(keyword, deliveryLocation));
//        return success(response);
//    }

    @GetMapping("/like-and-join")
    public ApiResult<List<RestaurantSearchResult>> searchRestaurantsUsingLikeAndJoin(
            @RequestParam("keyword") String keyword,
            @RequestParam("deliveryLocation") String deliveryLocation) {
        List<RestaurantSearchResult> response = searchService.searchLikeAndJoin(
                SearchRestaurantCommand.of(keyword, deliveryLocation));
        return success(response);
    }

    @GetMapping("/like-after-join")
    public ApiResult<List<RestaurantSearchResult>> searchRestaurantsUsingLikeAfterJoin(
            @RequestParam("keyword") String keyword,
            @RequestParam("deliveryLocation") String deliveryLocation) {
        List<RestaurantSearchResult> response = searchService.searchLikeAfterJoin(
                SearchRestaurantCommand.of(keyword, deliveryLocation));
        return success(response);
    }
}
