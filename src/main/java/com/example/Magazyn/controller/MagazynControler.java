package com.example.Magazyn.controller;

import com.example.Magazyn.model.Magazyn;
import com.example.Magazyn.service.MagazynServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MagazynControler {

    @Autowired
    private MagazynServiceImpl magazynServiceImpl;


    @GetMapping("/magazyny")
    public ModelAndView getAllProduct(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("magazyny/magazyny");
        modelAndView.addObject("magazyny", magazynServiceImpl.getAllMagazyn());
        modelAndView.addObject("LoggedUser", authentication);

        return modelAndView;
    }

    @GetMapping("/magazyny/addForm")
    public ModelAndView addFormMagazyn(Authentication authentication)
    {
        ModelAndView modelAndView = new ModelAndView("magazyny/addForm");
        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }

    @PostMapping("/magazyny/dodaj")
    public String addMagazyn(@RequestParam(value = "nazwa") String nazwa,
                             @RequestParam(value = "lokalizacja") String lokalizacja,
                             @RequestParam(value = "dlugosc") int dlugosc,
                             @RequestParam(value = "szerokosc") int szerokosc,
                             @RequestParam(value = "wysokosc") int wysokosc,
                             @RequestParam(value = "odstepy") int odstepy,
                             @RequestParam(value = "xWejscie") int xWejscie,
                             @RequestParam(value = "yWejscie") int yWejscie,

                             HttpServletRequest request,
                           RedirectAttributes redirectAttributes)
    {
        Magazyn magazyn = new Magazyn(nazwa,lokalizacja,dlugosc,szerokosc,wysokosc,odstepy,xWejscie,yWejscie);
        magazynServiceImpl.createMagazyn(magazyn);
        redirectAttributes.addFlashAttribute("wiadomosc", "Dodano magazyn");

        return "redirect:/magazyny";
    }
}
