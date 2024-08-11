package woowa.team4.bff.search.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.search.domain.RestaurantSearchResult;
import woowa.team4.bff.search.service.SearchIndexManageService;
import woowa.team4.bff.search.service.SearchService;
import woowa.team4.bff.search.service.command.CreateRestaurantSearchCommand;
import woowa.team4.bff.search.service.command.SearchRestaurantCommand;

@RequestMapping("/api/v1/search")
@RestController
@RequiredArgsConstructor
public class SearchApiController {
    private final SearchService searchService;
    private final SearchIndexManageService searchIndexManageService;

    @GetMapping("")
    public ResponseEntity<List<RestaurantSearchResult>> searchRestaurants(
            @RequestParam("keyword") String keyword,
            @RequestParam("deliveryLocation") String deliveryLocation) {
        List<RestaurantSearchResult> response = searchService.search(SearchRestaurantCommand.of(keyword, deliveryLocation));
        return ResponseEntity.ok(response);
    }

    // test 용 으로 만든 api
    @PostMapping("")
    public Long addRestaurant(@RequestParam("restaurantName") String restaurantName, @RequestParam("restaurantId") Long restaurantId){
        Long response = searchIndexManageService.addRestaurant(CreateRestaurantSearchCommand.of(restaurantId, restaurantName));
        return response;
    }
}
