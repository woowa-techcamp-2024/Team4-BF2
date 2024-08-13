package woowa.team4.bff.review.controller.create;


import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

public record ReviewCreateRequest(@NotBlank String content,
                                  @DecimalMin(value = "0.0") @DecimalMax(value = "5.0") Double rating) {

}