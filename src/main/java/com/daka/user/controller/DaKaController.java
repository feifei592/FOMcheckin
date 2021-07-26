package com.daka.user.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DaKaController {

    @RequestMapping("daka")
    @CrossOrigin
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
