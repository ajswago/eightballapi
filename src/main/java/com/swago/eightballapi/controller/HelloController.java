package com.swago.eightballapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping("")
    public String index() {
        return "Hello World!";
    }
}
