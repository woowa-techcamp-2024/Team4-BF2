package woowa.team4.bff.review.service;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReviewCreateResult {

    String reviewUuid;
    List<String> reviewMenuUuids;

}
