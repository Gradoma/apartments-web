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
        log.debug("init completed, encoding: " + encoding);
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        String requestEncoding = req.getCharacterEncoding();
        log.debug("requestEncoding: " + requestEncoding);
        if(requestEncoding == null || !requestEncoding.equalsIgnoreCase(encoding)){
            req.setCharacterEncoding(encoding);
            resp.setCharacterEncoding(encoding);
        }
        log.debug("Request code: " + req.getCharacterEncoding() + ", Response code: " + resp.getCharacterEncoding());
        chain.doFilter(req, resp);

    }public void destroy() {
        encoding = null;
    }
}
