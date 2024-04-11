package pl.dc2software.first.kubernetes.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/test")
public class FirstController {

    @GetMapping
    public String testGet() {
        return "Hello world";
    }

}
