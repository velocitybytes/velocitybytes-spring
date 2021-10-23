package com.searchslice.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.Instant;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {

    private final Object data;

    private final Boolean success;

    private final String timestamp;

    private final String cause;

    private final String path;

    public ApiResponse(Boolean success, String data, String cause, String path) {
        this.data = data;
        this.success = success;
        this.cause = cause;
        this.path = path;
        this.timestamp = Instant.now().toString();
    }

    public ApiResponse(Boolean success, Object data) {
        this.timestamp = Instant.now().toString();
        this.success = success;
        this.data = data;
        this.cause = null;
        this.path = null;
    }
}
