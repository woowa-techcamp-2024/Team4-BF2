package woowa.team4.bff.search.dummy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import woowa.team4.bff.search.document.MenuSearchDocument;
import woowa.team4.bff.search.document.RestaurantSearchDocument;
import woowa.team4.bff.search.repository.MenuSearchRepository;
import woowa.team4.bff.search.repository.RestaurantSearchRepository;

@RequestMapping("/api/v1/dummy")
@RestController
@RequiredArgsConstructor
public class DummyApiController {
    private final RestaurantSearchRepository restaurantSearchRepository;
    private final MenuSearchRepository menuSearchRepository;
    @PostMapping("/restaurants/{idx}")
    public void bulkInsertRestaurant(@PathVariable("idx") Integer idx){
        String csvFile = "restaurant_es_" + idx + ".csv";
        readRestaurant(csvFile);
    }

    private List<RestaurantSearchDocument> readRestaurant(String filename){
        List<RestaurantSearchDocument> restaurantSearchDocuments = new ArrayList<>();
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
                    restaurantSearchRepository.saveAll(restaurantSearchDocuments);
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
                System.out.println(Arrays.toString(data));
                RestaurantSearchDocument restaurantSearchDocument = RestaurantSearchDocument.builder()
                        .restaurantName(restaurantName)
                        .restaurantId(restaurantId)
                        .build();
                restaurantSearchDocuments.add(restaurantSearchDocument);
            }
            restaurantSearchRepository.saveAll(restaurantSearchDocuments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return restaurantSearchDocuments;
    }


    @PostMapping("/menus/{idx}")
    public void bulkInsertMenu(@PathVariable("idx") Integer idx){
        String csvFile = "menu_es_" + idx + ".csv";
        readMenu(csvFile);
    }

    private List<MenuSearchDocument> readMenu(String filename){
        List<MenuSearchDocument> menuSearchDocuments = new ArrayList<>();
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
                    menuSearchRepository.saveAll(menuSearchDocuments);
                    menuSearchDocuments = new ArrayList<>();
                    System.out.println(count + "개 처리");
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
                System.out.println(Arrays.toString(data));
                MenuSearchDocument menuSearchDocument = MenuSearchDocument.builder()
                        .menuName(menuName)
                        .menuId(menuId)
                        .restaurantId(restaurantId)
                        .build();
                menuSearchDocuments.add(menuSearchDocument);
            }
            menuSearchRepository.saveAll(menuSearchDocuments);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return menuSearchDocuments;
    }
}
