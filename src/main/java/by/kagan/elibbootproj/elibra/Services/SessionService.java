package by.kagan.elibbootproj.elibra.Services;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Service
public class SessionService {
    private static HttpSession CURRENTSESSION;

    public static void setCURRENTSESSION(HttpSession CURRENTSESSION) {
        SessionService.CURRENTSESSION = CURRENTSESSION;
    }

    public static HttpSession getCURRENTSESSION() {
        return CURRENTSESSION;
    }
    public static void invalidateCURRENTSESSION(){
        CURRENTSESSION.invalidate();
    }
}
