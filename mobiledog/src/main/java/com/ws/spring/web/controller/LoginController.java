package com.ws.spring.web.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

	@RequestMapping(value="/", method = RequestMethod.GET)
    public String index(ModelMap model){
        model.put("date", new Date());
        return "index";
    }
}
