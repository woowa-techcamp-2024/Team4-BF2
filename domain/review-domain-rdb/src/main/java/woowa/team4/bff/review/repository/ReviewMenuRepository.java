package woowa.team4.bff.review.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import woowa.team4.bff.domain.common.utils.PrefixedUuidConverter;
import woowa.team4.bff.review.domain.ReviewMenu;
import woowa.team4.bff.review.entity.ReviewMenuEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewMenuRepository {

    private final ReviewMenuEntityRepository reviewMenuEntityRepository;

    public List<ReviewMenu> saveAll(List<ReviewMenu> reviewMenu) {
        List<ReviewMenuEntity> reviewMenuEntity = reviewMenu.stream()
                .map(this::toEntity)
                .toList();
        reviewMenuEntityRepository.saveAll(reviewMenuEntity);

        return reviewMenuEntity.stream()
                .map(this::toDomain)
                .toList();
    }

    private ReviewMenuEntity toEntity(ReviewMenu reviewMenu) {
        return ReviewMenuEntity.builder()
                .reviewId(reviewMenu.getReviewId())
                .menuId(reviewMenu.getMenuId())
                .content(reviewMenu.getContent())
                .isLiked(reviewMenu.getIsLiked())
                .build();
    }

    private ReviewMenu toDomain(ReviewMenuEntity reviewMenuEntity) {
        return ReviewMenu.builder()
                .reviewMenuUuid(PrefixedUuidConverter.addPrefix("review_menu", reviewMenuEntity.getUuid()))
                .reviewId(reviewMenuEntity.getReviewId())
                .menuId(reviewMenuEntity.getMenuId())
                .content(reviewMenuEntity.getContent())
                .isLiked(reviewMenuEntity.getIsLiked())
                .build();
    }


}
