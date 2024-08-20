package woowa.team4.bff.dummy.controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.search.domain.MenuSearch;
import woowa.team4.bff.search.domain.RestaurantSearch;
import woowa.team4.bff.search.service.BulkInsertService;

@Slf4j
@RequestMapping("/api/v1/dummy")
@RestController
@Component
@RequiredArgsConstructor
public class DummyApiController {
    private final BulkInsertService bulkInsertRestaurant;

    @PostMapping("/restaurants/{idx}")
    public void bulkInsertRestaurant(Integer idx){
        String csvFile = "restaurant_es_" + idx + ".csv";
        readRestaurant(csvFile);
    }

    private void readRestaurant(String filename){
        List<RestaurantSearch> restaurantSearchDocuments = new ArrayList<>();
        int batch_size = 10000;
        int i = 0;
        int count = 0;

        try (InputStream inputStream = getClass().getResourceAsStream("/" + filename);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            br.readLine(); // 헤더 건너뛰기
            while ((line = br.readLine()) != null) {
                i+=1;
                count += 1;
                if(i == batch_size){
                    i = 0;
                    bulkInsertRestaurant.bulkInsertRestaurant(restaurantSearchDocuments);
                    restaurantSearchDocuments = new ArrayList<>();
                    System.out.println(count + "개 처리");
                }
                String[] data = line.split(",");
                String restaurantName = data[1];
                Long restaurantId = Long.valueOf(data[data.length-1]);
                if(data.length > 3){
                    for(int c=2; c < data.length-1; c++){
                        restaurantName += ",";
                        restaurantName += data[c];
                    }
                }
                log.info(Arrays.toString(data));
                RestaurantSearch restaurantSearchDocument = RestaurantSearch.builder()
                        .restaurantName(restaurantName)
                        .restaurantId(restaurantId)
                        .build();
                restaurantSearchDocuments.add(restaurantSearchDocument);
            }
            bulkInsertRestaurant.bulkInsertRestaurant(restaurantSearchDocuments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //@PostMapping("/menus/{idx}")
    public void bulkInsertMenu(Integer idx){
        String csvFile = "menu_es_" + idx + ".csv";
        readMenu(csvFile);
    }

    private void readMenu(String filename){
        List<MenuSearch> menuSearchDocuments = new ArrayList<>();
        int batch_size = 10000;
        int i = 0;
        int count = 0;

        try (InputStream inputStream = getClass().getResourceAsStream("/" + filename);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {

            String line;
            br.readLine(); // 헤더 건너뛰기
            while ((line = br.readLine()) != null) {
                i+=1;
                count += 1;
                if(i == batch_size){
                    i = 0;
                    bulkInsertRestaurant.bulkInsertMenu(menuSearchDocuments);
                    menuSearchDocuments = new ArrayList<>();
                    log.info(count + "개 처리");
                }
                String[] data = line.split(",");
                String menuName = data[1];
                Long menuId = Long.valueOf(data[data.length-2]);
                Long restaurantId = Long.valueOf(data[data.length-1]);;
                if(data.length > 4){
                    for(int c=2; c < data.length-2; c++){
                        menuName += ",";
                        menuName += data[c];
                    }
                }
                log.info(Arrays.toString(data));
                MenuSearch menuSearchDocument = MenuSearch.builder()
                        .menuName(menuName)
                        .menuId(menuId)
                        .restaurantId(restaurantId)
                        .build();
                menuSearchDocuments.add(menuSearchDocument);
            }
            bulkInsertRestaurant.bulkInsertMenu(menuSearchDocuments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
