package one.terenin.exception;

import one.terenin.exception.common.ErrorCode;

public class BaseException extends RuntimeException{

    public BaseException(ErrorCode message) {
        super(message.getMeaning());
    }
}
