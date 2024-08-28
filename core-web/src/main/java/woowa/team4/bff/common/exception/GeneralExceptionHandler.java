package woowa.team4.bff.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import woowa.team4.bff.common.utils.ApiUtils;
import woowa.team4.bff.common.utils.ApiUtils.ApiResult;

/**
 * 전역 예외 처리를 위한 클래스이다. 이 클래스는 애플리케이션에서 발생하는 다양한 예외를 잡아 적절한 HTTP 응답으로 변환한다.
 */
@Slf4j
@ControllerAdvice
public class GeneralExceptionHandler {

    /**
     * 예외와 HTTP 상태 코드를 받아 표준화된 오류 응답을 생성한다.
     *
     * @param throwable 발생한 예외
     * @param status    HTTP 상태 코드
     * @return 오류 정보를 포함한 ResponseEntity
     */
    private ResponseEntity<ApiResult<?>> newResponse(Throwable throwable,
            HttpStatus status) {
        return newResponse(throwable.getMessage(), status);
    }

    /**
     * 오류 메시지와 HTTP 상태 코드를 받아 표준화된 오류 응답을 생성한다.
     *
     * @param message 오류 메시지
     * @param status  HTTP 상태 코드
     * @return 오류 정보를 포함한 ResponseEntity
     */
    private ResponseEntity<ApiResult<?>> newResponse(String message, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<>(ApiUtils.error(message, status), headers, status);
    }

    /**
     * 요청한 리소스를 찾을 수 없을 때 발생하는 예외를 처리한다.
     *
     * @param e NoHandlerFoundException
     * @return 404 Not Found 응답
     */
    @ExceptionHandler({NoHandlerFoundException.class})
    public ResponseEntity<?> handleNotFoundException(Exception e) {
        return newResponse(e, HttpStatus.NOT_FOUND);
    }

    /**
     * 잘못된 요청 파라미터나 유효성 검사 실패 시 발생하는 예외를 처리한다.
     *
     * @param e IllegalArgumentException, IllegalStateException, 또는 MethodArgumentNotValidException
     * @return 400 Bad Request 응답
     */
    @ExceptionHandler({IllegalArgumentException.class, IllegalStateException.class,
            MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleBadRequestException(Exception e) {
        log.error("잘못된 요청", e);
        if (e instanceof MethodArgumentNotValidException) {
            return newResponse(
                    ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors().get(0)
                            .getDefaultMessage(),
                    HttpStatus.BAD_REQUEST);
        }
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 지원하지 않는 미디어 타입으로 요청이 왔을 때 발생하는 예외를 처리한다.
     *
     * @param e HttpMediaTypeException
     * @return 415 Unsupported Media Type 응답
     */
    @ExceptionHandler(HttpMediaTypeException.class)
    public ResponseEntity<?> handleHttpMediaTypeException(Exception e) {
        return newResponse(e, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    /**
     * 지원하지 않는 HTTP 메서드로 요청이 왔을 때 발생하는 예외를 처리한다.
     *
     * @param e HttpRequestMethodNotSupportedException
     * @return 405 Method Not Allowed 응답
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<?> handleMethodNotAllowedException(Exception e) {
        return newResponse(e, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * HTTP 요청 본문을 읽을 수 없을 때 발생하는 예외를 처리한다.
     *
     * @param e HttpMessageNotReadableException
     * @return 400 Bad Request 응답
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(Exception e) {
        return newResponse(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * 그 외 모든 예외를 처리한다.
     *
     * @param e Exception 또는 RuntimeException
     * @return 500 Internal Server Error 응답
     */
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<?> handleException(Exception e) {
        log.error("서버 오류", e);
        return newResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
