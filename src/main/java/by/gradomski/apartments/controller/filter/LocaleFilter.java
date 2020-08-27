package by.gradomski.apartments.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LocaleFilter", urlPatterns = {"/control"})
public class LocaleFilter implements Filter {
    private static final Logger log = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.debug("start locale filter");
        HttpServletRequest reqHttp = (HttpServletRequest) req;
        String userLanguageChoice = reqHttp.getParameter("language");
        HttpServletResponse respHttp = (HttpServletResponse) resp;
        HttpSession session = reqHttp.getSession(true);
        String localeString = (String) session.getAttribute("locale");
        if(userLanguageChoice != null){
            log.debug("user set language: " + userLanguageChoice);
            session.setAttribute("locale", userLanguageChoice);
            respHttp.sendRedirect(reqHttp.getHeader("referer"));
        } else if(localeString == null ){
            log.debug("default lang null, set lang...");
            session.setAttribute("locale", "en");
            chain.doFilter(req, resp);
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void destroy() {
    }
}
