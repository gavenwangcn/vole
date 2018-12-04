package com.github.vole.demo.controller;

import com.github.vole.demo.fegin.TraceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @Autowired
    TraceService traceService;

    @GetMapping("/trace/{name}")
    public String demoTrace(@PathVariable String name) {

        return traceService.trace(name);
    }
}
