package woowa.team4.bff.search.service;


import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Component;
import woowa.team4.bff.search.document.RestaurantMenusDocument;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component // 빈으로 등록하면 스프링 시작 시 실행됩니다. Elasticsearch 인덱스를 초기화하는 용도입니다!
@RequiredArgsConstructor
public class ElasticsearchIndexInitializer implements CommandLineRunner {

    private static final int BATCH_SIZE = 10_000;
    private static final String INDEX_NAME = "restaurant_menus";

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public void run(String... args) throws Exception {
        IndexOperations indexOps = elasticsearchOperations.indexOps(RestaurantMenusDocument.class);

        if (indexOps.exists()) {
            log.info("Elasticsearch 인덱스 '{}' 이미 존재합니다. 초기화를 건너뜁니다.", INDEX_NAME);
            return;
        }

        log.info("Elasticsearch nested 도큐먼트 인덱스 초기화 시작");
        Map<String, RestaurantMenusDocument> restaurantMap = readRestaurants("updated_restaurants_es.csv");
        readMenus("updated_menu_es.csv", restaurantMap);
//        saveToElasticsearch(restaurantMap.values());
        bulkSaveToElasticsearch(restaurantMap.values());
        log.info("Elasticsearch nested 도큐먼트 인덱스 초기화 완료");
    }

    private Map<String, RestaurantMenusDocument> readRestaurants(String filename) throws IOException, CsvValidationException {
        Map<String, RestaurantMenusDocument> restaurantMap = new HashMap<>();
        CSVParser parser = new CSVParserBuilder().withSeparator(',').withQuoteChar('"').build();

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(getClass().getResourceAsStream("/" + filename)))
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

        try (CSVReader reader = new CSVReaderBuilder(new InputStreamReader(getClass().getResourceAsStream("/" + filename)))
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
            }
        }

        if (!queries.isEmpty()) {
            elasticsearchOperations.bulkIndex(queries, elasticsearchOperations.getIndexCoordinatesFor(RestaurantMenusDocument.class));
        }
    }
}
