package com.gundaero.alley.exception;

import com.gundaero.alley.common.AuthErrorStatus;
import com.gundaero.alley.common.HttpStatusCode;
import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

@Getter
public class AuthErrorException extends AuthenticationException {
    private final HttpStatusCode code;
    private final String errorMsg;
    private final AuthErrorStatus errorStatus;

    public AuthErrorException(AuthErrorStatus authStatus){
        super(authStatus.getMsg());
        this.code = authStatus.getStatusCode();
        this.errorMsg = authStatus.getMsg();
        this.errorStatus = authStatus;
    }


}