package woowa.team4.bff.review.controller.create;

import java.util.List;

public record ReviewCreateResponse (String reviewId, List<String> reviewMenuIds) {

        public static ReviewCreateResponse of(String reviewId, List<String> reviewMenuIds) {
            return new ReviewCreateResponse(reviewId, reviewMenuIds);
        }
}
