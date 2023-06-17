package one.terenin.security.util;

import jakarta.servlet.http.HttpServletRequest;

public interface ExtractorHeaderUtil {

    boolean isHeaderPresent(HttpServletRequest request);
    String extractToken(HttpServletRequest request);

}
