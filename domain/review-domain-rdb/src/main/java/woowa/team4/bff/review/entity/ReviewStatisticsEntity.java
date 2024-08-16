package woowa.team4.bff.review.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "review_statistics")
@Getter
@NoArgsConstructor
public class ReviewStatisticsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long restaurantId;

    private Long reviewCount;

    private Double averageRating;

    @Builder
    public ReviewStatisticsEntity(Long id, Long restaurantId, Long reviewCount, Double averageRating) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
    }

}
