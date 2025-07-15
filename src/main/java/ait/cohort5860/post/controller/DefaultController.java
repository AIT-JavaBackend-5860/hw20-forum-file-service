package ait.cohort5860.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultController {
    @GetMapping("/")
    public String hello() {
        return "Forum File Service is running.";
    }
}
