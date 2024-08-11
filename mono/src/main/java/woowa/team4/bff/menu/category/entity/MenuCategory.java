package woowa.team4.bff.menu.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "menu_categories")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MenuCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "restaurant_uuid")
    private String restaurantUuid;

    private String name;

    private String description;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = "menu_category_" + UUID.randomUUID();
    }

    private MenuCategory(final String restaurantUuid,
            final String name, final String description) {
        this.restaurantUuid = restaurantUuid;
        this.name = name;
        this.description = description;
    }

    public static MenuCategory create(final String restaurantUuid,
            final String name, final String description) {
        return new MenuCategory(restaurantUuid, name, description);
    }

    public void update(final String name, final String description) {
        this.name = name;
        this.description = description;
    }
}
