package woowa.team4.bff.restauarntexposureservice.exposure.utils;

import lombok.ToString;

/**
 * API 응답을 표준화하기 위한 유틸리티 클래스이다. 이 클래스는 성공 및 오류 응답을 생성하는 메서드와 응답 형식을 정의하는 내부 클래스를 제공한다.
 */
public class ApiUtils {

    /**
     * 성공적인 API 응답을 생성
     *
     * @param response 응답 데이터
     * @param <T>      응답 데이터의 타입
     * @return 성공 상태와 응답 데이터를 포함한 ApiResult 객체
     */
    public static <T> ApiResult<T> success(T response) {
        return new ApiResult<>(true, response, null);
    }

    /**
     * API 응답 결과를 나타내는 내부 클래스
     *
     * @param <T> 응답 데이터의 타입
     */
    @ToString
    public static class ApiResult<T> {

        private final boolean success;
        private final T response;
        private final String error;

        private ApiResult(boolean success, T response, String error) {
            this.success = success;
            this.response = response;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getError() {
            return error;
        }

        public T getResponse() {
            return response;
        }
    }
}
