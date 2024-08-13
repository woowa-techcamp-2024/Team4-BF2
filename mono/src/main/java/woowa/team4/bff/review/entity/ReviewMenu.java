package woowa.team4.bff.review.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import woowa.team4.bff.menu.item.entity.MenuEntity;

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
    private MenuEntity menuEntity;

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
