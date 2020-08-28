package by.gradomski.apartments.controller.filter;

import by.gradomski.apartments.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "RedirectSecurityFilter", urlPatterns = { "/jsp/*" },
        initParams = { @WebInitParam(name = "SIGN_IN_PATH", value = "/index.jsp") })
public class RedirectSecurityFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    String signInPath;

    public void init(FilterConfig config) throws ServletException {
        signInPath = config.getInitParameter("SIGN_IN_PATH");
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        log.debug("start redirect security filter");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null){
            log.debug("user is null...");
            resp.sendRedirect(req.getContextPath() + signInPath);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
