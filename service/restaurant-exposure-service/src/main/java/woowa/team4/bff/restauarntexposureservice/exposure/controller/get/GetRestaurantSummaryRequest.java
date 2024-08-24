package woowa.team4.bff.restauarntexposureservice.exposure.controller.get;

public record GetRestaurantSummaryRequest(String keyword, String deliveryLocation, Integer pageNumber) {
}
