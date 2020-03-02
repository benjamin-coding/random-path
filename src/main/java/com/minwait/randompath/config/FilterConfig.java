package com.minwait.randompath.config;

import com.minwait.randompath.filter.PathFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FilterConfig
 *
 * @author by xunmi
 * @version 1.0
 * @date 2020/3/2 10:55
 */
@Configuration
public class FilterConfig {
    @Value(value = "${path.pattern}")
    private String pathPattern;

    @Bean
    public FilterRegistrationBean pathFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new PathFilter());
        registration.addInitParameter("path.pattern", this.pathPattern);
        registration.addUrlPatterns("/*");
        registration.setName("pathFilter");
        registration.setOrder(1);
        return registration;
    }
}
