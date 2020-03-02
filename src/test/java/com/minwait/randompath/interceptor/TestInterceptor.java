package com.minwait.randompath.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TestInterceptor
 *
 * @author by xunmi
 * @version 1.0
 * @date 2020/3/2 11:03
 */
public class TestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }

}