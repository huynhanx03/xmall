package com.xmall.common.shared.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

public enum ErrorCode {
    // Generic error codes
    UNCATEGORIZED_EXCEPTION(504, HttpStatus.INTERNAL_SERVER_ERROR, "An unknown error occurred."),
    INVALID_KEY(999, HttpStatus.BAD_REQUEST, "Invalid message key."),
    EXISTED(1001, HttpStatus.BAD_REQUEST, ""),
    INVALID(1002, HttpStatus.BAD_REQUEST, ""),
    NOT_EXISTED(1003, HttpStatus.NOT_FOUND, ""),
    FAILED(1004, HttpStatus.NOT_FOUND, ""),


    // HTTP-related error codes
    HTTP_BAD_REQUEST(400, HttpStatus.BAD_REQUEST, "Bad request. Please check your input."),
    HTTP_UNAUTHORIZED(401, HttpStatus.UNAUTHORIZED, "Unauthorized access."),
    HTTP_NOT_FOUND(404, HttpStatus.NOT_FOUND, "The requested resource was not found."),
    HTTP_INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error. Please try again later.");

    private int code;
    private HttpStatusCode httpStatusCode;
    private String message;

    ErrorCode(int code, HttpStatusCode httpStatusCode, String message) {
        this.code = code;
        this.httpStatusCode = httpStatusCode;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatusCode getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(HttpStatusCode httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
