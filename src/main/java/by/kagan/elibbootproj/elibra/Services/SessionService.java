package by.kagan.elibbootproj.elibra.Services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionService {
    private static HttpSession CURRENTSESSION;

    public static void setCURRENTSESSION(HttpSession CURRENTSESSION) {
        SessionService.CURRENTSESSION = CURRENTSESSION;
    }

    public static HttpSession getCURRENTSESSION() {
        return CURRENTSESSION;
    }
}
