package woowa.team4.bff.searchservice;


import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.interfaces.SearchService;
import woowa.team4.bff.api.client.request.SearchRequest;
import woowa.team4.bff.api.client.response.SearchResponse;


@RequestMapping("/api/v1/search")
@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @PostMapping("")
    public SearchResponse findIdsByKeywordAndDeliveryLocation(@RequestBody SearchRequest request){
        List<Long> restaurantIds = searchService.findIdsByKeywordAndDeliveryLocation(request.getKeyword(), request.getDeliveryLocation(), request.getPageNumber());
        return SearchResponse.builder()
                .ids(restaurantIds)
                .build();
    }
}
