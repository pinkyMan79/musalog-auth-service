package one.terenin.exception.children;

import one.terenin.exception.BaseException;
import one.terenin.exception.common.ErrorCode;

public class ServiceNotFoundException extends BaseException {
    public ServiceNotFoundException(ErrorCode message) {
        super(message);
    }
}
