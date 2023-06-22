package one.terenin.exception.children;

import one.terenin.exception.BaseException;
import one.terenin.exception.common.ErrorCode;

public class AuthorizationException extends BaseException {
    public AuthorizationException(ErrorCode message) {
        super(message);
    }
}
