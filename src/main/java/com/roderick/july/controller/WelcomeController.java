package com.roderick.july.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Roderick on 2016/7/27.
 */
@Controller
@RequestMapping("/")
public class WelcomeController {

    @RequestMapping
    public String welcome(Model model) {
        model.addAttribute("title", "july");
        model.addAttribute("msg", "欢迎你");
        return "/WEB-INF/views/welcome.jsp";
    }
}
