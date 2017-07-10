package com.mentoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ivanovaolyaa
 * @version 7/10/2017
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String printHelloWorld() {
        return "hello";
    }
}
