package woowa.team4.bff.review.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import woowa.team4.bff.menu.item.entity.Menu;

import java.time.LocalDateTime;

@Entity
@Table(name = "review_menus")
@Getter
@NoArgsConstructor
public class ReviewMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @ManyToOne
    private Review review;

    @OneToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private Boolean isLiked;

    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = "review_menu" + java.util.UUID.randomUUID();
    }


}
