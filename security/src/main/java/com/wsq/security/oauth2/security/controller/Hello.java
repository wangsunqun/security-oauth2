package com.wsq.security.oauth2.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO
 *
 * @author wsq
 * 2021/1/5 11:40
 */
@RestController
public class Hello {
    @GetMapping("hello/world")
    public String hello() {
        return "hello world";
    }

    @GetMapping("hello1")
    public String hello1() {
        return "hello world1";
    }

    @GetMapping("hello2")
    public String hello2() {
        return "hello world2";
    }

    @GetMapping("hello3")
    public String hello3() {
        return "hello world3";
    }
}
