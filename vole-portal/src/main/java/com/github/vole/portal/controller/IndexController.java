package com.github.vole.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {


    @RequestMapping("index.html")
    public String index(Model model) {
        return "ftl/index";
    }
}
