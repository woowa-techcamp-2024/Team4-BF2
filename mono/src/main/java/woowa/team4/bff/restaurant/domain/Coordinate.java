package woowa.team4.bff.restaurant.domain;

public record Coordinate(double latitude, double longitude) {

    public Coordinate {
        if (latitude < -90 || latitude > 90) {
            throw new IllegalArgumentException("위도는 -90 ~ 90 범위이어야 합니다.");
        }
        if (longitude < -180 || longitude > 180) {
            throw new IllegalArgumentException("경도는 -180 ~ 180 범위이어야 합니다.");
        }
    }
}
