package woowa.team4.bff.menu.item.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woowa.team4.bff.menu.item.dto.create.MenuCreateDto;
import woowa.team4.bff.menu.item.dto.update.MenuUpdateDto;

@Entity
@Table(name = "menus")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "menu_category_id")
    private Long menuCategoryId;

    private String name;

    private String description;

    private BigDecimal price;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = "menu_" + java.util.UUID.randomUUID();
    }

    private Menu(final Long menuCategoryId, final String name,
            final String description, final BigDecimal price) {
        this.menuCategoryId = menuCategoryId;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public static Menu create(final Long menuCategoryId, final MenuCreateDto menuCreateDto) {
        return new Menu(menuCategoryId, menuCreateDto.name(),
                menuCreateDto.description(), menuCreateDto.price());
    }

    public void update(final MenuUpdateDto menuUpdateDto) {
        this.name = menuUpdateDto.name();
        this.description = menuUpdateDto.description();
        this.price = menuUpdateDto.price();
    }
}
