package woowa.team4.bff.common.utils;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.http.HttpStatus;

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
     * 예외로 인한 오류 API 응답을 생성
     *
     * @param throwable 발생한 예외
     * @param status    HTTP 상태 코드
     * @return 오류 상태와 에러 정보를 포함한 ApiResult 객체
     */
    public static ApiResult<?> error(Throwable throwable, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(throwable, status));
    }

    /**
     * 메시지로 인한 오류 API 응답을 생성
     *
     * @param message 오류 메시지
     * @param status  HTTP 상태 코드
     * @return 오류 상태와 에러 정보를 포함한 ApiResult 객체
     */
    public static ApiResult<?> error(String message, HttpStatus status) {
        return new ApiResult<>(false, null, new ApiError(message, status));
    }

    /**
     * API 오류 정보를 나타내는 내부 클래스
     */
    public static class ApiError {

        private final String message;
        private final int status;

        ApiError(Throwable throwable, HttpStatus status) {
            this(throwable.getMessage(), status);
        }

        ApiError(String message, HttpStatus status) {
            this.message = message;
            this.status = status.value();
        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("message", message)
                    .append("status", status)
                    .toString();
        }
    }

    /**
     * API 응답 결과를 나타내는 내부 클래스
     *
     * @param <T> 응답 데이터의 타입
     */
    public static class ApiResult<T> {

        private final boolean success;
        private final T response;
        private final ApiError error;

        private ApiResult(boolean success, T response, ApiError error) {
            this.success = success;
            this.response = response;
            this.error = error;
        }

        public boolean isSuccess() {
            return success;
        }

        public ApiError getError() {
            return error;
        }

        public T getResponse() {
            return response;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                    .append("success", success)
                    .append("response", response)
                    .append("error", error)
                    .toString();
        }
    }
}
