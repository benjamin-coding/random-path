package com.minwait.randompath.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * PathFilter
 *
 * @author by xunmi
 * @version 1.0
 * @date 2020/3/2 10:33
 */

public class PathFilter implements Filter {
    private static final String PATH_DELIMITER = "/";

    private String pattern;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.pattern = filterConfig.getInitParameter("path.pattern");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        System.out.println(req.getServletPath());
        System.out.println(req.getRequestURI());
        System.out.println(req.getContextPath());
        System.out.println(req.getRequestURL());
        String servletPath = req.getServletPath();
        if (Pattern.matches(this.pattern, servletPath)) {
            filterChain.doFilter(new InternalRequest(req), servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }


    private class InternalRequest extends HttpServletRequestWrapper {

        InternalRequest(HttpServletRequest request) {
            super(request);
        }

        /**
         * Spring mvc 是通过ServletPath来确定调用哪个Controller的
         *
         * @return
         */
        @Override
        public String getServletPath() {
            String servletPath = super.getServletPath();
            //删除第一个路径，如/Ajgj8dsa_random/user/add -> /user/add
            String[] pathParts = servletPath.split(PATH_DELIMITER);
            String[] newPathParts = Arrays.copyOfRange(pathParts, 2, pathParts.length);
            return PATH_DELIMITER + joinPath(newPathParts);
        }

        private String joinPath(String[] array) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; i++) {
                if (i > 0) {
                    sb.append(PATH_DELIMITER);
                }
                sb.append(array[i]);
            }
            return sb.toString();
        }
    }
}