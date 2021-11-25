package com.example.springproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String getPageIndex(){
        return "feedCreate";
    }

    @GetMapping("/feeds")
    public String getFeedList(){
        return "feedList"; }
}
