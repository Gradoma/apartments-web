package by.gradomski.apartments.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/control"})
public class LocaleFilter implements Filter {
    private static final Logger log = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //TODO(fix)
        log.debug("start doFilter");
        log.debug("req: " + req + ", resp: " + resp);
        HttpServletRequest reqHttp = (HttpServletRequest) req;
        String userLanguageChoice = reqHttp.getParameter("language");
        log.debug("user choose locale: " + userLanguageChoice);
        HttpServletResponse respHttp = (HttpServletResponse) resp;
        HttpSession session = reqHttp.getSession(true);
        log.debug("session id: " + session.getId());
        String localeString = (String) session.getAttribute("locale");
        log.debug("current locale: " + localeString);
        if(userLanguageChoice != null){
            session.setAttribute("locale", userLanguageChoice);
            log.debug("redirect to: " + reqHttp.getHeader("referer"));
            respHttp.sendRedirect(reqHttp.getHeader("referer"));
        } else if(localeString == null ){
            session.setAttribute("locale", "en");
            log.debug("redirect to: " + reqHttp.getHeader("referer"));
            respHttp.sendRedirect(reqHttp.getHeader("referer"));
        } else {
            log.debug("lang wasn't changed");
            chain.doFilter(req, resp);
        }
    }

    public void destroy() {
    }
}
