package by.gradomski.apartments.controller.filter;

import by.gradomski.apartments.entity.Role;
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

@WebFilter(filterName = "AdminRedirectFilter", urlPatterns = {"/jsp/admin/*"},
        initParams = { @WebInitParam(name = "USER_PATH", value = "/jsp/user_page.jsp") })
public class AdminRedirectFilter implements Filter {
    String userPath;
    private static final Logger log = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
        userPath = config.getInitParameter("USER_PATH");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.debug("start admin redirect filter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user.getRole() != Role.ADMIN){
            log.debug("user is not admin");
            response.sendRedirect(request.getContextPath() + userPath);
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
