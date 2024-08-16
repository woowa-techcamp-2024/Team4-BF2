package woowa.team4.bff.review.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "review_menus")
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class ReviewMenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID uuid;

    private Long reviewId;

    private Long menuId;

    private Boolean isLiked;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = java.util.UUID.randomUUID();
    }

    @Builder
    public ReviewMenuEntity(final Long reviewId, final Long menuId, final String content, final Boolean isLiked) {
        this.reviewId = reviewId;
        this.menuId = menuId;
        this.content = content;
        this.isLiked = isLiked;
    }

}
