package com.example.Magazyn.controller;

import com.example.Magazyn.service.MagazynServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
    @Autowired
    MagazynServiceImpl magazynServiceImpl;

    @GetMapping({"/"})
    public ModelAndView indexPage(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("magazyny/magazyny");
        modelAndView.addObject("magazyny", magazynServiceImpl.getAllMagazyn());
        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


}
