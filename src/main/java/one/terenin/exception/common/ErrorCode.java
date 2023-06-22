package one.terenin.exception.common;

public enum ErrorCode {
    UNAUTHORIZED("User unauthorized, try again login"),
    USER_NOT_FOUND("User not found in user service. Rejected"),
    USER_PASSWORD_INVALID("User was founded by login, but password incorrect. Try again"),
    USER_ROLE_NOT_FOUND("Role not found. Rejected"),
    SERVICE_NOT_FOUND("Service not found. Unknown instance"),
    SERVICE_CALL_REJECTED("Service call rejected"),
    TOKEN_GENERATION_REJECTED("Something went wrong on token generation process, check generation class");

    private final String meaning;

    ErrorCode(String meaning) {
        this.meaning = meaning;
    }

    public String getMeaning() {
        return meaning;
    }
}
