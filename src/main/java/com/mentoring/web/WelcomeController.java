package com.mentoring.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ivanovaolyaa
 * @version 7/31/2017
 */
@Controller
public class WelcomeController {

    @RequestMapping("/")
    public String welcome() {
        return "index";
    }
}
