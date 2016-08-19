package com.roderick.july.controller;


import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Roderick on 2016/7/27.
 */
@Controller
@RequestMapping("/")
public class WelcomeController implements EnvironmentAware {
    Environment env = null;
    @RequestMapping(value={"/", "/index"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        model.addAttribute("title", "july");
        model.addAttribute("msg", "欢迎你");
        return "/WEB-INF/views/welcome.jsp";
    }

    public void setEnvironment(Environment environment) {
        this.env = environment;
    }
}
