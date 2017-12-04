package com.mentoring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ivanovaolyaa
 * @version 7/31/2017
 */
@Controller
public class WelcomeController {

    @GetMapping("/")
    public String welcome() {
        return "index";
    }
}
