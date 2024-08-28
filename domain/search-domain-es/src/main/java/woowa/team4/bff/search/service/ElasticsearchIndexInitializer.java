package woowa.team4.bff.search.service;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;
import woowa.team4.bff.search.document.RestaurantMenusDocument;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ElasticsearchIndexInitializer implements CommandLineRunner {

    private static final int BATCH_SIZE = 10_000;
    private static final String INDEX_NAME = "restaurant_menus";

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void run(String... args) throws Exception {
        IndexOperations indexOps = elasticsearchOperations.indexOps(RestaurantMenusDocument.class);

        if (indexOps.exists()) {
            NativeQuery countQuery = NativeQuery.builder()
                    .withQuery(query -> query.matchAll(matchAll -> matchAll))
                    .build();
            SearchHits<RestaurantMenusDocument> searchHits = elasticsearchOperations.search(countQuery, RestaurantMenusDocument.class);
            long documentCount = searchHits.getTotalHits();

            if (documentCount > 0) {
                log.info("Elasticsearch 인덱스 '{}' 에 {} 개의 문서가 존재합니다. 초기화를 건너뜁니다.", INDEX_NAME, documentCount);
                return;
            }
            log.info("Elasticsearch 인덱스 '{}' 가 존재하지만 비어 있습니다. 초기화를 진행합니다.", INDEX_NAME);
        } else {
            log.info("Elasticsearch 인덱스 '{}' 가 존재하지 않습니다. 새로 생성합니다.", INDEX_NAME);
            indexOps.create();
        }

        log.info("Elasticsearch nested 도큐먼트 인덱스 초기화 시작");
        Map<String, RestaurantMenusDocument> restaurantMap = readRestaurants("updated_restaurants_es.csv");
        readMenus("updated_menu_es.csv", restaurantMap);
        bulkSaveToElasticsearch(restaurantMap.values());
        log.info("Elasticsearch nested 도큐먼트 인덱스 초기화 완료");
    }

    private Map<String, RestaurantMenusDocument> readRestaurants(String filename) throws IOException, CsvValidationException {
        Map<String, RestaurantMenusDocument> restaurantMap = new HashMap<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();

        InputStream resourceStream = getClass().getResourceAsStream("/" + filename);
        if (resourceStream == null) {
            log.info("{} 파일이 없습니다. 건너뛸게요!", filename);
            return restaurantMap;
        }
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(resourceStream))
                .withCSVParser(parser)
                .build()) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                RestaurantMenusDocument restaurant = RestaurantMenusDocument.builder()
                        .id(line[2])
                        .restaurantName(line[1])
                        .restaurantId(Long.parseLong(line[2]))
                        .deliveryLocation(line[3])
                        .menus(new ArrayList<>())
                        .build();
                restaurantMap.put(line[2], restaurant);
            }
        }
        return restaurantMap;
    }

    private void readMenus(String filename, Map<String, RestaurantMenusDocument> restaurantMap) throws IOException, CsvValidationException {
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();

        InputStream resourceStream = getClass().getResourceAsStream("/" + filename);
        if (resourceStream == null) {
            log.info("{} 파일이 없습니다. 건너뛸게요!", filename);
            return;
        }
        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(resourceStream))
                .withCSVParser(parser)
                .build()) {
            String[] line;
            reader.readNext(); // Skip header
            while ((line = reader.readNext()) != null) {
                String restaurantId = line[3];
                RestaurantMenusDocument restaurant = restaurantMap.get(restaurantId);
                if (restaurant != null) {
                    RestaurantMenusDocument.MenuDocument menuDocument = new RestaurantMenusDocument.MenuDocument(line[1], Long.parseLong(line[2]));
                    restaurant.getMenus().add(menuDocument);
                }
            }
        }
    }

    private void bulkSaveToElasticsearch(Iterable<RestaurantMenusDocument> documents) {
        List<IndexQuery> queries = new ArrayList<>();
        int count = 0;

        for (RestaurantMenusDocument document : documents) {
            IndexQuery indexQuery = new IndexQueryBuilder()
                    .withId(document.getId())
                    .withObject(document)
                    .build();
            queries.add(indexQuery);
            count++;

            if (count % BATCH_SIZE == 0) {
                elasticsearchOperations.bulkIndex(queries, elasticsearchOperations.getIndexCoordinatesFor(RestaurantMenusDocument.class));
                queries.clear();
                log.info("Bulk indexing {} documents", count);
            }
        }

        if (!queries.isEmpty()) {
            elasticsearchOperations.bulkIndex(queries, elasticsearchOperations.getIndexCoordinatesFor(RestaurantMenusDocument.class));
        }
    }
}
