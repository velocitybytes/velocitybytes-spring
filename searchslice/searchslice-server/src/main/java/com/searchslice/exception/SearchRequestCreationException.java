package com.searchslice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class SearchRequestCreationException extends RuntimeException {

    public SearchRequestCreationException(String url, String message) {
        super(String.format("Failed to create search item [%s] : '%s'", url, message));
    }
}
