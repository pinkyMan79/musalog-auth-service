package one.terenin.exception.children;

import one.terenin.exception.BaseException;
import one.terenin.exception.common.ErrorCode;

public class TokenException extends BaseException {
    public TokenException(ErrorCode message) {
        super(message);
    }
}
