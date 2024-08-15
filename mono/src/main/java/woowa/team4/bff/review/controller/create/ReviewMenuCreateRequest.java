package woowa.team4.bff.review.controller.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewMenuCreateRequest(@NotBlank String menuUuid, @NotNull String content,
                                      @NotNull Boolean isLiked) {

}
