package by.gradomski.apartments.controller.filter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"},
            initParams = {@WebInitParam(name = "encoding", value = "UTF-8", description = "encoding param")})
public class EncodingFilter implements Filter {
    private static final Logger log = LogManager.getLogger();
    private String encoding;

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        log.debug("start encode filter");
        String requestEncoding = req.getCharacterEncoding();
        if(requestEncoding == null || !requestEncoding.equalsIgnoreCase(encoding)){
            log.debug("encoding problem: " + requestEncoding);
            req.setCharacterEncoding(encoding);
            resp.setCharacterEncoding(encoding);
        }
        chain.doFilter(req, resp);
    }

    public void destroy() {
        encoding = null;
    }
}
