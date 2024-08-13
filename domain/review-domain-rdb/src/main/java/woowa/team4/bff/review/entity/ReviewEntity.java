package woowa.team4.bff.review.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "restaurant_uuid")
    private UUID restaurantUuid;

    private String content;

    private Double rating;

    @OneToMany(mappedBy = "review")
    private List<ReviewMenuEntity> reviewMenuEntities;

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID();
    }

    @Builder
    public ReviewEntity(final UUID restaurantUuid, final String content, final Double rating) {
        this.restaurantUuid = restaurantUuid;
        this.content = content;
        this.rating = rating;
    }

}
