package com.august.run.domain.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping("/index")
    public String index() {
        return "Hellow. Bye.";
    }
}
