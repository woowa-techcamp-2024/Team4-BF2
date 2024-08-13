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
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "restaurants")
@EntityListeners(AuditingEntityListener.class)
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String phone;

    private String address;

    private String introduction;

    private String image;

    private String operatingTime;

    private String closedDays;

    private Integer minimumOrderAmount;

    private UUID uuid;
    private String deliveryLocation;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public RestaurantEntity() {
    }

    @Builder
    public RestaurantEntity(String name, String phone, String address, String introduction, String image, String operatingTime, String closedDays, Integer minimumOrderAmount, String deliveryLocation) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.introduction = introduction;
        this.image = image;
        this.operatingTime = operatingTime;
        this.closedDays = closedDays;
        this.minimumOrderAmount = minimumOrderAmount;
        this.deliveryLocation = deliveryLocation;
    }

    @PrePersist
    public void setupUuid() {
        this.uuid = UUID.randomUUID();
    }
}
