package com.example.codelabsvc.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {
    /**
     * Response content
     */
    T result;

    /**
     * Error code. Null if responseCode indicates success.
     */
    String errorCode;

    /**
     * "OK" if response is successful. Otherwise contains error details.
     */
    Object message;

    /**
     * 200 on success, 400 (or other codes) on error.
     */
    int responseCode;

    /**
     * Create a successful response
     *
     * @param result Response result
     * @param <T>    Type of result
     * @return Successful response object
     */
    public static <T> ApiResponse<T> successWithResult(T result) {
        return new ApiResponse<>(result, null, HttpStatus.OK.getReasonPhrase(), HttpStatus.OK.value());
    }

    public static <T> ApiResponse<T> successWithResult(T result, String message) {
        return new ApiResponse<>(result, null, message, HttpStatus.OK.value());
    }

    /**
     * Create a failed response
     *
     * @param errorCode Error code
     * @param message   Error message
     * @param <T>       Type of result
     * @return Failed response object
     */

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message) {
        return new ApiResponse<>(null, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, String message, T result) {
        return new ApiResponse<>(result, errorCode, message, HttpStatus.BAD_REQUEST.value());
    }

    public static <T> ApiResponse<T> failureWithCode(String errorCode, Object message, T result, HttpStatus httpStatus) {
        return new ApiResponse<>(result, errorCode, message, httpStatus.value());
    }
}
