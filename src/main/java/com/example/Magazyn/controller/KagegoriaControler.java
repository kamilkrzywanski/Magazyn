package com.example.Magazyn.controller;

import com.example.Magazyn.model.Kategoria;
import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.service.KategoriaServiceImpl;
import com.example.Magazyn.service.ProduktServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class KagegoriaControler {

    @Autowired
    KategoriaServiceImpl kategoriaServiceImpl;

    @Autowired
    ProduktServiceImpl produktServiceImpl;
    @GetMapping("/kategorie")
    public ModelAndView getAllKategoria(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("kategorie/kategorie");
        modelAndView.addObject("kategorie", kategoriaServiceImpl.getAllKategoria());
        modelAndView.addObject("LoggedUser", authentication);


        return modelAndView;
    }


    @GetMapping("/kategorie/addForm")
    public ModelAndView addFormKategoria(Authentication authentication)
    {
        ModelAndView modelAndView = new ModelAndView("/kategorie/addForm");
        modelAndView.addObject("kategorie", kategoriaServiceImpl.getAllKategoria());
        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


    @PostMapping("/kategorie/dodaj")
    public String addMagazyn(@RequestParam(value = "nazwa") String nazwa,
                             Authentication authentication,
                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes)
    {
        Kategoria kategoria = new Kategoria(nazwa);
        kategoriaServiceImpl.createKategoria(kategoria);
        redirectAttributes.addFlashAttribute("wiadomosc", "Dodano kategorię");

        return "redirect:/kategorie";
    }


    @GetMapping("/kategorie/usun/{idKategoria}")
    public String removeOdznaka(@PathVariable Integer idKategoria,
                                RedirectAttributes redirectAttributes) {

        if(produktServiceImpl.getAllByKategoria_idKategoria(idKategoria) >0 )
            redirectAttributes.addFlashAttribute("success_msg", "Nie można usunąć kategorii, proszą usunąć produkty, które do niej należą");

        else
        {
            kategoriaServiceImpl.removeKategoria(idKategoria);
            redirectAttributes.addFlashAttribute("success_msg", "Usunięto wiersz pomyślnie!");

        }

        return "redirect:/kategorie";
    }


    @GetMapping("/kategorie/updateForm")
    public String updateFormKategoria(@RequestParam(value = "id") Integer idKategoria,
                                    Model model,
                                    Authentication authentication) {

        model.addAttribute("LoggedUser", authentication);

       Kategoria kategoria = kategoriaServiceImpl.getOneById(idKategoria);

        model.addAttribute("kategoria", kategoria);


        return "kategorie/updateForm";
    }


    @PostMapping("/kategorie/update/{idKategoria}")
    public String updateKategoria(@RequestParam(value = "nazwa") String nazwa,
                                @PathVariable int idKategoria,
                                RedirectAttributes redirectAttributes) throws IOException {

        kategoriaServiceImpl.updateKategoria(idKategoria, nazwa);
     redirectAttributes.addFlashAttribute("success_msg", "Zmiany zostały przyjęte pomyślnie");
        return "redirect:/kategorie";
    }

}
