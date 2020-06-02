package com.haochuang.demobulb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BulbController {

    @GetMapping("/ceshi")
    public String cehis(){
        return "ceshi";
    }

}



