package airport.web.restful.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class XssFilter implements Filter {

    Logger LOG = LoggerFactory.getLogger(XssFilter.class);
    Logger INIT_LOG = LoggerFactory.getLogger("airport.web.initialize");

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        INIT_LOG.warn("server init");
        INIT_LOG.warn((String) (System.getProperties().get("java.class.path")));
    }

    @Override
    public void destroy() {
        LOG.info("destroy");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        long startTime = System.currentTimeMillis();

        chain.doFilter(new XssHttpServletRequestWrapper(req), response);

        String path = req.getRequestURI().split(";")[0];
        if (!path.startsWith("/static")) {
            // read req.getSession().getId() will break the JSESSIONID cookie, which shiro used to do auth.
            // LOG.info("request begin", t1, /* req.getSession().getId(),*/ getIpAddr(req), req.getHeader("Referer"));
            LOG.info(
                "request begin: from {}, target at {} with params {}, referred from {}, consume {} milliseconds",
                getIpAddr(req),
                path,
                URLDecoder.decode(req.getQueryString() == null ? "" : req.getQueryString()),
                req.getHeader("Referer"),
                System.currentTimeMillis() - startTime);
        }
    }

    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if ((ip == null) || (ip.length() == 0) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
