package com.github.vole.mps.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTraceController {

    @GetMapping("/rest/trace/{name}")
    public String trace(@PathVariable("name") String name){
        return "hello:"+name+" this is the mps trace service";
    }
}
