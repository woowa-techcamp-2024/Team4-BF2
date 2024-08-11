package woowa.team4.bff.restaurant.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woowa.team4.bff.restaurant.domain.Coordinate;

import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Table(name = "restaurant")
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

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public RestaurantEntity() {
    }

    @Builder
    public RestaurantEntity(String name, String phone, String address, Coordinate location, String introduction, String image, String operatingTime, String closedDays, Integer minimumOrderAmount, Long businessId) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.introduction = introduction;
        this.image = image;
        this.operatingTime = operatingTime;
        this.closedDays = closedDays;
        this.minimumOrderAmount = minimumOrderAmount;
        this.businessId = businessId;

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        this.location = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(location.longitude(), location.latitude()));
    }

    @PrePersist
    public void setupUuid() {
        this.uuid = UUID.randomUUID();
    }

    public Coordinate getLocation() {
        return new Coordinate(location.getY(), location.getX());
    }
}
