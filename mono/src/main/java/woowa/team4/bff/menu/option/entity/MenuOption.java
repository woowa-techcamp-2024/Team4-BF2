package woowa.team4.bff.menu.option.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import woowa.team4.bff.menu.option.dto.create.MenuOptionCreateDto;
import woowa.team4.bff.menu.option.dto.update.MenuOptionUpdateDto;

@Entity
@Table(name = "menu_options")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MenuOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @Column(name = "menu_id")
    private Long menuId;

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
        this.uuid = "menu_option_" + java.util.UUID.randomUUID();
    }

    private MenuOption(final Long menuId, final String name, final String description) {
        this.menuId = menuId;
        this.name = name;
        this.description = description;
    }

    public static MenuOption create(final Long menuId,
            final MenuOptionCreateDto menuOptionCreateDto) {
        return new MenuOption(menuId,
                menuOptionCreateDto.getName(),
                menuOptionCreateDto.getDescription());
    }

    public void update(final MenuOptionUpdateDto menuOptionUpdateDto) {
        this.name = menuOptionUpdateDto.getName();
        this.description = menuOptionUpdateDto.getDescription();
    }
}
