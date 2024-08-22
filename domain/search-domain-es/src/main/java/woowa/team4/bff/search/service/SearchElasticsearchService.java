package woowa.team4.bff.search.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import woowa.team4.bff.interfaces.SearchService;
import woowa.team4.bff.search.document.RestaurantMenusDocument;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchElasticsearchService implements SearchService {

    private static final int DEFAULT_PAGE_SIZE = 25;

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation, Integer pageNumber) {
        return searchRestaurants(deliveryLocation, keyword, pageNumber);
    }

    public List<Long> searchRestaurants(String deliveryLocation, String keyword, int pageNumber) {
        NativeQuery searchQuery = NativeQuery.builder()
                .withFilter(filter -> filter.term(term -> term.field("deliveryLocation").value(deliveryLocation)))
                .withQuery(query -> query.bool(bool -> bool
                        .should(restaurantQuery -> restaurantQuery.match(match -> match.field("restaurantName").query(keyword)))
                        .should(menuQuery -> menuQuery.nested(nested -> nested.path("menus")
                                .query(nestedQuery -> nestedQuery.match(match -> match.field("menus.menuName").query(keyword))))
                        )))
                .withPageable(PageRequest.of(pageNumber, DEFAULT_PAGE_SIZE))
                .build();
        SearchHits<RestaurantMenusDocument> searchHits = elasticsearchOperations.search(searchQuery, RestaurantMenusDocument.class);

        return searchHits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent().getRestaurantId())
                .toList();
    }
}
