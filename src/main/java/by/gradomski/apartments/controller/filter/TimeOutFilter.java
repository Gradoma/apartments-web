package by.gradomski.apartments.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "TimeOutFilter", urlPatterns = {"/*"},
        initParams = { @WebInitParam(name = "INDEX_PATH", value = "/index.jsp") })
public class TimeOutFilter implements Filter {
    String indexPath;
    private static final Logger log = LogManager.getLogger();

    public void init(FilterConfig config) throws ServletException {
        indexPath = config.getInitParameter("INDEX_PATH");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.debug("start time out filter");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);
        if (session == null){
            log.debug("session is null...");
            request.getSession();
            RequestDispatcher dispatcher = request.getRequestDispatcher(indexPath);     //TODO(doesn't work)
            dispatcher.forward(request, response);
//            response.sendRedirect(request.getContextPath() + indexPath);
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
