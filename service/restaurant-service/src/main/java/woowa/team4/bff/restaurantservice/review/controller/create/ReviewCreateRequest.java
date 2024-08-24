package woowa.team4.bff.restaurantservice.review.controller.create;


import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ReviewCreateRequest(@NotBlank String content,
                                  @DecimalMin(value = "0.0") @DecimalMax(value = "5.0") Double rating,
                                  @Valid List<ReviewMenuCreateRequest> menus) {

    public ReviewCreateRequest {
        menus = (menus != null) ? menus : List.of();
    }

}