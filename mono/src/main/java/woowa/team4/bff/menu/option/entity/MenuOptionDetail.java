package woowa.team4.bff.menu.option.entity;

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

@Entity
@Table(name = "menu_option_details")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class MenuOptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    @Column(name = "menu_option_id")
    private Long menuOptionId;

    private String name;

    private BigDecimal price;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.uuid = "menu_option_detail_" + java.util.UUID.randomUUID();
    }

    private MenuOptionDetail(final Long menuOptionId, final String name, final BigDecimal price) {
        this.menuOptionId = menuOptionId;
        this.name = name;
        this.price = price;
    }

    public static MenuOptionDetail create(final Long menuOptionId,
            final String name, final BigDecimal price) {
        return new MenuOptionDetail(menuOptionId, name, price);
    }

    public void update(final String name, final BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
