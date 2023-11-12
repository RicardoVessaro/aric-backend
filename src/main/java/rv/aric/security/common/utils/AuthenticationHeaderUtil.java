package rv.aric.security.common.utils;

import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationHeaderUtil {

    private final String header;

    public AuthenticationHeaderUtil(HttpServletRequest request) {
        this.header = request.getHeader("Authorization");
    }

    public String getHeader() {
        return this.header;
    }

    public String getJwtToken() {
        return isNull() ? null : this.header.substring(7);
    }

    public boolean isBearer() {
        return isNotNull() && this.header.startsWith("Bearer ");
    }

    public boolean isNotNull() {
        return !isNull();
    }

    public boolean isNull() {
        return this.header == null;
    }
}
