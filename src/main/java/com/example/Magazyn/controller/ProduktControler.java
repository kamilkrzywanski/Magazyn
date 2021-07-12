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
public class ProduktControler {

    @Autowired
    ProduktServiceImpl produktServiceImpl;

    @Autowired
    KategoriaServiceImpl kategoriaServiceImpl;

    @GetMapping("/produkty")
    public ModelAndView getAllProduct(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("produkty/produkty");
        modelAndView.addObject("produkty", produktServiceImpl.getAllProdukt());
        modelAndView.addObject("LoggedUser", authentication);


        return modelAndView;
    }


    @GetMapping("/produkty/addForm")
    public ModelAndView addFormMagazyn(Authentication authentication)
    {
        ModelAndView modelAndView = new ModelAndView("/produkty/addForm");
        modelAndView.addObject("kategorie", kategoriaServiceImpl.getAllKategoria());
        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


    @PostMapping("/produkty/dodaj")
    public String addMagazyn(@RequestParam(value = "nazwa") String nazwa,
                             @RequestParam(value = "dlugosc") int dlugosc,
                             @RequestParam(value = "szerokosc") int szerokosc,
                             @RequestParam(value = "wysokosc") int wysokosc,
                             @RequestParam(value = "selectKategoria") Kategoria kategoria,

                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes)
    {
        Produkt produkt = new Produkt(nazwa,0, kategoria,dlugosc,szerokosc,wysokosc);
        produktServiceImpl.createProdukt(produkt);
        redirectAttributes.addFlashAttribute("wiadomosc", "Dodano produkt");

        return "redirect:/produkty";
    }



    @GetMapping("/produkty/updateForm")
    public String updateFormProdukt(@RequestParam(value = "id") Integer idProdukt,
                                    Model model,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {

        if(produktServiceImpl.getOneById(idProdukt).getStanMagazynowy() ==0){
        model.addAttribute("LoggedUser", authentication);

        Produkt produkt = produktServiceImpl.getOneById(idProdukt);
        Kategoria kategoria = produkt.getKategoria();
        model.addAttribute("produkt", produkt);
        model.addAttribute("kategoria", kategoria);
        model.addAttribute("kategorie", kategoriaServiceImpl.getAllKategoria());


        return "produkty/updateForm";}
        else
        {
            redirectAttributes.addFlashAttribute("error_msg", "Produkt jest ulokowany na półkach, brak możliwości edycji");
            return "redirect:/produkty";
        }

    }


    @GetMapping("/produkty/delete")
    public String deleteProdukt(@RequestParam(value = "id") Integer idProdukt,
                                    Model model,
                                    Authentication authentication,
                                    RedirectAttributes redirectAttributes) {

        if(produktServiceImpl.getOneById(idProdukt).getStanMagazynowy() !=0){
            model.addAttribute("LoggedUser", authentication);

            redirectAttributes.addFlashAttribute("error_msg", "Nie można usunąć towaru ulokowanego na regałach");
            return "redirect:/produkty";

           }
        else
        {
            produktServiceImpl.removeProdukt(idProdukt);
            redirectAttributes.addFlashAttribute("success_msg", "Towar usunięty pomyślnie");

            return "redirect:/produkty";
        }

    }


    @PostMapping("/produkty/update/{idProdukt}")
    public String updateProdukt(@RequestParam(value = "nazwa") String nazwa,
                                @RequestParam(value = "dlugosc") int dlugosc,
                                @RequestParam(value = "szerokosc") int szerokosc,
                                @RequestParam(value = "wysokosc") int wysokosc,
                                @RequestParam(value = "selectKategoria") Kategoria kategoria,
                                @PathVariable Integer idProdukt,
                                RedirectAttributes redirectAttributes) throws IOException {

        Produkt produkt = produktServiceImpl.getOneById(idProdukt);

        produkt.setNazwa(nazwa);
        produkt.setStanMagazynowy(produkt.getStanMagazynowy());
        produkt.setDlugosc(dlugosc);
        produkt.setSzerokosc(szerokosc);
        produkt.setWysokosc(wysokosc);
        produkt.setKategoria(kategoria);

        produktServiceImpl.createProdukt(produkt);
        redirectAttributes.addFlashAttribute("success_msg", "Zmiany zostały przyjęte pomyślnie");
        return "redirect:/produkty";
    }



}
