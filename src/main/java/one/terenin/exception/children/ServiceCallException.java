package one.terenin.exception.children;

import one.terenin.exception.BaseException;
import one.terenin.exception.common.ErrorCode;

public class ServiceCallException extends BaseException {
    public ServiceCallException(ErrorCode message) {
        super(message);
    }
}
