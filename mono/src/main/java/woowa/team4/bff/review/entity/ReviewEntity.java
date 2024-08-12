package woowa.team4.bff.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowa.team4.bff.review.command.ReviewCreateCommand;

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
    private String restaurantUuid;

    private String content;

    private Double rating;

    @OneToMany(mappedBy = "review")
    private List<ReviewMenu> reviewMenus;

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID();
    }

    public ReviewEntity(final String restaurantUuid, final String content, final Double rating) {
        this.restaurantUuid = restaurantUuid;
        this.content = content;
        this.rating = rating;
    }

    public static ReviewEntity create(final String restaurantUuid, ReviewCreateCommand command) {
        return new ReviewEntity(restaurantUuid, command.content(), command.rating());
    }

}
