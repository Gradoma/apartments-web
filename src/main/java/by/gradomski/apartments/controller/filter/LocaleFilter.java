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

@WebFilter(urlPatterns = {"/jsp/header.jsp"}, filterName = "LocaleFilter")
public class LocaleFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.debug("start doFilter");
        log.debug("req: " + req + ", resp: " + resp);
        HttpServletRequest reqHttp = (HttpServletRequest) req;
        HttpServletResponse respHttp = (HttpServletResponse) resp;
        HttpSession session = reqHttp.getSession(true);
        Locale locale = (Locale) session.getAttribute("lang");
        if(locale == null){
            log.debug("locale is null");
            locale = new Locale("en");
            session.setAttribute("lang", locale);
        }
        respHttp.setLocale(locale);
        log.debug("redirect to: " + reqHttp.getHeader("referer"));
        respHttp.sendRedirect(reqHttp.getHeader("referer"));
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
