package com.minwait.randompath.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TestController
 *
 * @author by xunmi
 * @version 1.0
 * @date 2020/3/2 11:01
 */
@RestController
@RequestMapping
public class TestController {

    @RequestMapping("hello")
    public String hello(String name) {
        return "Hello " + name;
    }

    @RequestMapping("forbidden/hello")
    public String forbidden(String name) {
        return "Forbidden Hello " + name;
    }
}
