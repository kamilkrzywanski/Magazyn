package com.example.Magazyn.controller;


import com.example.Magazyn.model.Magazyn;
import com.example.Magazyn.model.Pracownik;
import com.example.Magazyn.model.Regal;
import com.example.Magazyn.model.Zamowienie;
import com.example.Magazyn.service.MagazynServiceImpl;
import com.example.Magazyn.service.RegalServiceImpl;
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
import java.awt.*;
import java.io.IOException;
import java.util.Date;
import java.util.List;


@Controller
public class RegalController {


    @Autowired
    RegalServiceImpl regalServiceImpl;

    @Autowired
    MagazynServiceImpl magazynServiceImpl;


    @GetMapping("/regalyMagazynu/{idMagazyn}")
    public ModelAndView getAllRegalForMagazyn(@PathVariable int idMagazyn, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("regaly/regalyMagazynu");
        modelAndView.addObject("regaly", regalServiceImpl.getAllRegalByIdMagazyn(idMagazyn));
        modelAndView.addObject("magazyn", magazynServiceImpl.getOneById(idMagazyn));
        modelAndView.addObject("LoggedUser", authentication);

        return modelAndView;
    }


    @GetMapping("/regalyMagazynu/addForm/{idMagazyn}")
    public ModelAndView addFormRegal(Authentication authentication,  @PathVariable Integer idMagazyn )
    {
        ModelAndView modelAndView = new ModelAndView("/regaly/addForm");
        modelAndView.addObject("regaly", regalServiceImpl.getAllRegal());
        modelAndView.addObject("idMagazyn", idMagazyn);
        modelAndView.addObject("magazyn", magazynServiceImpl.getOneById(idMagazyn));

        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


    @PostMapping("/regalyMagazynu/dodaj")
    public String addRegal(@RequestParam(value = "ilosc_polek") int iloscPolek,
                                @RequestParam(value = "x_poczatku") int xPoczatek,
                                @RequestParam(value = "y_poczatku") int yPoczatek,
                                @RequestParam(value = "wysokosc") int wysokosc,
                                @RequestParam(value = "szerokosc") int szerokosc,
                                @RequestParam(value = "dlugosc") int dlugosc,

                           @RequestParam(value = "magazyn") Magazyn magazyn,

                           HttpServletRequest request,
                                RedirectAttributes redirectAttributes)
    {

        Magazyn magazynLoc = magazynServiceImpl.getOneById(magazyn.getIdMagazyn());


        Regal regal = new Regal(xPoczatek, yPoczatek, szerokosc,dlugosc, iloscPolek, wysokosc,magazyn);

        List<Regal> regalList = regalServiceImpl.getAllRegalByIdMagazyn(magazyn.getIdMagazyn());

        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        int countererror = 0;
        int initError = 0;
        int odstep = magazynLoc.getOdstepy();



      if( magazyn.getSzerokosc()*100< xPoczatek+szerokosc ||  magazyn.getDlugosc()*100 < yPoczatek+dlugosc)
            initError++;

            int x1 = xPoczatek-odstep;
            int y1 = yPoczatek-odstep;
            int h1 = wysokosc+odstep;
            int w1 = szerokosc+odstep;

                for(int i = 0; i < regalList.stream().count(); i++) {

                    {
                        int x2 = regalList.get(i).getxPoczatek();
                        int y2 = regalList.get(i).getyPoczatek();
                        int h2 = regalList.get(i).getWysokosc();
                        int w2 = regalList.get(i).getSzerokosc();

                        {
                            if (x1 < x2 + w2 &&
                                    x1 + w1 > x2 &&
                                    y1 < y2 + h2 &&
                                    y2 + h1 > y2) countererror++;
                            else  countererror = 0;
                        }
//


                        }



                    }







        if(countererror < 1 && initError ==0 )
        {
            regalServiceImpl.createRegal(regal);
            redirectAttributes.addFlashAttribute("wiadomosc", "Dodano regal");

        }

        else
            redirectAttributes.addFlashAttribute("error_msg", "Podano nieprawidłowe wymiary regału");



        return "redirect:/regalyMagazynu/"+magazyn.getIdMagazyn();
    }





    @GetMapping("/regaly/updateForm")
    public String updateFormZamowienie(@RequestParam(value = "id") Integer idRegal,
                                       Model model,
                                       Authentication authentication) {

        model.addAttribute("LoggedUser", authentication);

        Regal regal = regalServiceImpl.getOneById(idRegal);
        model.addAttribute("regal", regal);
        model.addAttribute("magazyny", magazynServiceImpl.getAllMagazyn());


        return "regaly/updateForm";
    }


    @PostMapping("/regaly/update/{idRegal}")
    public String updateZamowienie(@RequestParam(value = "ilosc_polek") int iloscPolek,
                                   @RequestParam(value = "x_poczatku") int xPoczatek,
                                   @RequestParam(value = "y_poczatku") int yPoczatek,
                                   @RequestParam(value = "x_konca") int xKoniec,
                                   @RequestParam(value = "y_konca") int yKoniec,
                                   @RequestParam(value = "wysokosc") int wysokosc,
                                   @RequestParam(value = "szerokosc") int szerokosc,
                                   @RequestParam(value = "dlugosc") int dlugosc,


                                   @RequestParam(value = "selectMagazyn") Magazyn magazyn,

                                   @PathVariable Integer idRegal,
                                   RedirectAttributes redirectAttributes) throws IOException {

        Regal regal = regalServiceImpl.getOneById(idRegal);

        regal.setIloscPolek(iloscPolek);
        regal.setxPoczatek(xPoczatek);
        regal.setyPoczatek(yPoczatek);
        regal.setDlugosc(dlugosc);
        regal.setSzerokosc(szerokosc);
        regal.setWysokosc(wysokosc);
        regal.setMagazyn(magazyn);


        regalServiceImpl.createRegal(regal);
        redirectAttributes.addFlashAttribute("success_msg", "Zmiany zostały przyjęte pomyślnie");
        return "redirect:/regalyMagazynu/"+magazyn.getIdMagazyn();
    }


}
