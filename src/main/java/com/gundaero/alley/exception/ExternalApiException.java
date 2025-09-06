package com.gundaero.alley.exception;

import com.gundaero.alley.common.ErrorStatus;
import lombok.Getter;

@Getter
public class ExternalApiException extends RuntimeException {
    private final ErrorStatus errorStatus;

    public ExternalApiException(ErrorStatus errorStatus, String detailMessage) {
        super(detailMessage);
        this.errorStatus = errorStatus;
    }
}
