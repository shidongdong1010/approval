package com.itanbank.account.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dongdongshi on 16/1/13.
 */
@Controller
public class IndexController {

    @RequestMapping("/index.html")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/404")
    public String error404(){
        return "error-404";
    }

    @RequestMapping("/500")
    public String error500(){
        return "error-500";
    }

    @RequestMapping("/403")
    public String error403(){
        return "error-403";
    }
}
