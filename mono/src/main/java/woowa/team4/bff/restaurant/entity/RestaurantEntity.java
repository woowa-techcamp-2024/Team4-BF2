package woowa.team4.bff.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.geo.Point;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woowa.team4.bff.restaurant.domain.Coordinate;

import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Entity
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String address;

    private Point location;

    private String introduction;

    private String image;

    private String operatingTime;

    private String closedDays;

    private Integer minimumOrderAmount;

    private Long businessId;

    private UUID uuid;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public Coordinate getLocation() {
        return new Coordinate(location.getX(), location.getY());
    }
}
