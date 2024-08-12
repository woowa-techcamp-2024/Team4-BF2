package woowa.team4.bff.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowa.team4.bff.review.command.ReviewCreateCommand;

import java.util.List;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "restaurant_uuid")
    private String restaurantUuid;

    private String content;

    private Double rating;

    @OneToMany(mappedBy = "review")
    private List<ReviewMenu> reviewMenus;

    @PrePersist
    public void prePersist() {
        this.uuid = "review_" + java.util.UUID.randomUUID();
    }

    public Review(final String restaurantUuid, final String content, final Double rating) {
        this.restaurantUuid = restaurantUuid;
        this.content = content;
        this.rating = rating;
    }

    public static Review create(final String restaurantUuid, ReviewCreateCommand command) {
        return new Review(restaurantUuid, command.content(), command.rating());
    }

}
