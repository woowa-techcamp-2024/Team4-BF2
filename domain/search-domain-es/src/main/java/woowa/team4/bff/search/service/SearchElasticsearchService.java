package woowa.team4.bff.search.service;

import co.elastic.clients.elasticsearch._types.SortOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.stereotype.Service;
import woowa.team4.bff.interfaces.SearchService;
import woowa.team4.bff.search.document.RestaurantMenusDocument;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchElasticsearchService implements SearchService {

    private static final int DEFAULT_MAX_SIZE = 250;

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Long> findIdsByKeywordAndDeliveryLocation(String keyword, String deliveryLocation, Integer pageNumber) {
        NativeQuery searchQuery = buildNativeQuery(keyword, deliveryLocation);
        SearchHits<RestaurantMenusDocument> searchHits = elasticsearchOperations.search(searchQuery, RestaurantMenusDocument.class);
        return searchHits.getSearchHits()
                .stream()
                .map(hit -> hit.getContent().getRestaurantId())
                .toList();
    }

    private NativeQuery buildNativeQuery(String keyword, String deliveryLocation) {
        return NativeQuery.builder()
                .withFilter(filter -> filter.term(term -> term.field("deliveryLocation").value(deliveryLocation)))
                .withQuery(query -> query.bool(bool -> bool
                        .should(restaurantQuery -> restaurantQuery.match(match -> match.field("restaurantName").query(keyword)))
                        .should(menuQuery -> menuQuery.nested(nested -> nested.path("menus")
                                .query(nestedQuery -> nestedQuery.match(match -> match.field("menus.menuName").query(keyword))))
                        )))
                .withSort(sort -> sort.score(score -> score.order(SortOrder.Desc)))
                .withSourceFilter(FetchSourceFilter.of(new String[]{"restaurantId"}, null))
                .withPageable(Pageable.ofSize(DEFAULT_MAX_SIZE))
                .build();
    }
}
