package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @RequestMapping(value = "/test")
    public String getMessage(){

        return "hello world, opay............test";
    }

    @RequestMapping(value = "/check")
    public String checkMessage(){
        return "check wechat is ok..";
    }
}
