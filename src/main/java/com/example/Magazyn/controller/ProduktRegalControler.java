package com.example.Magazyn.controller;


import com.example.Magazyn.model.*;
import com.example.Magazyn.service.MagazynServiceImpl;
import com.example.Magazyn.service.ProduktRegalServiceImpl;
import com.example.Magazyn.service.ProduktServiceImpl;
import com.example.Magazyn.service.RegalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class ProduktRegalControler {

    @Autowired
    ProduktRegalServiceImpl produktRegalServiceImpl;

    @Autowired
    MagazynServiceImpl magazynServiceImpl;

    @Autowired
    ProduktServiceImpl produktServiceImpl;

    @Autowired
   RegalServiceImpl regalServiceImpl;
    @GetMapping("/regalProdukty/{idRegal}")
    public ModelAndView getAllProduktyForRegal(@PathVariable int idRegal, Authentication authentication) {

        Magazyn magazyn = regalServiceImpl.getOneById(idRegal).getMagazyn();

        ModelAndView modelAndView = new ModelAndView("produktyRegal/produktyRegal");
        modelAndView.addObject("produktyRegal", produktRegalServiceImpl.getAllProduktRegalByIdRegal(idRegal));
        modelAndView.addObject("idRegal", idRegal);
        modelAndView.addObject("LoggedUser", authentication);
        modelAndView.addObject("magazyn", magazyn);


        return modelAndView;
    }


    @GetMapping("/regalProdukty/addForm/{idRegal}")
    public ModelAndView addFormProduktRegal(Authentication authentication,  @PathVariable Integer idRegal )
    {
        ModelAndView modelAndView = new ModelAndView("/produktyRegal/addForm");
        modelAndView.addObject("produkty", produktServiceImpl.getAllProdukt());
        modelAndView.addObject("regal", regalServiceImpl.getOneById(idRegal));

        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


    @PostMapping("/regalProdukty/dodaj")
    public String addProduktRegal(@RequestParam(value = "idProdukt") Produkt idProdukt,
                           @RequestParam(value = "idRegal") Regal idRegal,
                           @RequestParam(value = "ilosc") int ilosc,
                           @RequestParam(value = "polka") int polka,

                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes)
    {

        int nowaIlosc = idProdukt.getStanMagazynowy();
        nowaIlosc-=ilosc;
        idProdukt.setStanMagazynowy(nowaIlosc);

        int nrKolejnosc = 0;
         List<ProduktRegal> produktRegaltmp = produktRegalServiceImpl.getAllProduktRegalByIdRegal(idRegal.getIdRegal());

        for (int i = 0; i < produktRegaltmp.size(); i++) {


            if (produktRegaltmp.get(i).getNrKolejnosc() > nrKolejnosc && produktRegaltmp.get(i).getPolka() == polka)
                nrKolejnosc = produktRegaltmp.get(i).getNrKolejnosc();
        }

        for(int x = 0; x < ilosc; x++) {

            if (idProdukt.getDlugosc() > idProdukt.getSzerokosc())
                nrKolejnosc += idProdukt.getSzerokosc();
            else
                nrKolejnosc += idProdukt.getDlugosc();

            ProduktRegal produktRegal = new ProduktRegal(idProdukt, idRegal, polka, nrKolejnosc);
            produktRegalServiceImpl.createProduktRegal(produktRegal);
        }




        redirectAttributes.addFlashAttribute("wiadomosc", "Dodano produkt");

        return "redirect:/regalProdukty/"+idRegal.getIdRegal();
    }


    

    @GetMapping("/dostawy")
    public ModelAndView addFormProduktRegal(Authentication authentication )
    {
        ModelAndView modelAndView = new ModelAndView("/Dostawy/addForm");
        modelAndView.addObject("produkty", produktServiceImpl.getAllProdukt());
        modelAndView.addObject("magazyny", magazynServiceImpl.getAllMagazyn());
        modelAndView.addObject("regaly", regalServiceImpl.getAllRegal());

        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


    @PostMapping("/dostawy/dodaj/{idRegal}")
    public String dostawa(@RequestParam(value = "idProdukt") Produkt idProdukt,
                                  @PathVariable Integer idRegal,
                                  @RequestParam(value = "ilosc") int ilosc,
                                  @RequestParam(value = "polka") int polka,

                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes)
    {

        int nrKolejnosc = 0;


        List<ProduktRegal> produktRegaltmp = produktRegalServiceImpl.getAllProduktRegalByIdRegal(idRegal);

        Regal regal = regalServiceImpl.getOneById(idRegal);
        int iloscPolek = regal.getIloscPolek();
        int wymiarRegalu;
        if(regal.getSzerokosc() > regal.getDlugosc())
        {

          wymiarRegalu = regal.getSzerokosc();


        }
        else
            {
                wymiarRegalu = regal.getDlugosc();

            }

        int countError =0;
        for(int x = 0; x < ilosc; x++) {
            for (int i = 0; i < produktRegaltmp.size(); i++) {


                if (produktRegaltmp.get(i).getNrKolejnosc() > nrKolejnosc && produktRegaltmp.get(i).getPolka() % 10 == polka)
                    nrKolejnosc = produktRegaltmp.get(i).getNrKolejnosc();

            }

            if (idProdukt.getDlugosc() > idProdukt.getSzerokosc())
                nrKolejnosc += idProdukt.getSzerokosc() * 10000 + polka;
            else
                nrKolejnosc += idProdukt.getDlugosc() * 10000 + polka;


            if(nrKolejnosc/10000 < wymiarRegalu)
            {
                int nowaIlosc = idProdukt.getStanMagazynowy();
                nowaIlosc+=ilosc;
                idProdukt.setStanMagazynowy(nowaIlosc);

                ProduktRegal produktRegal = new ProduktRegal(idProdukt, regalServiceImpl.getOneById(idRegal), polka, nrKolejnosc);
                produktRegalServiceImpl.createProduktRegal(produktRegal);



            }
            else
                countError++;

        }



        if(countError == 0)
        redirectAttributes.addFlashAttribute("wiadomosc", "Dodano towary do magazynu");
        else
            redirectAttributes.addFlashAttribute("wiadomosc", ("Brak miejsca na regale"));

        return "redirect:/dostawy/";
    }




//    @GetMapping("/regaly/updateForm")
//    public String updateFormZamowienie(@RequestParam(value = "id") Integer idRegal,
//                                       Model model,
//                                       Authentication authentication) {
//
//        model.addAttribute("LoggedUser", authentication);
//
//        Regal regal = regalServiceImpl.getOneById(idRegal);
//        model.addAttribute("regal", regal);
//        model.addAttribute("magazyny", magazynServiceImpl.getAllMagazyn());
//
//
//        return "regaly/updateForm";
//    }
//
//
//    @PostMapping("/regaly/update/{idRegal}")
//    public String updateZamowienie(@RequestParam(value = "ilosc_polek") int iloscPolek,
//                                   @RequestParam(value = "x_poczatku") int xPoczatek,
//                                   @RequestParam(value = "y_poczatku") int yPoczatek,
//                                   @RequestParam(value = "x_konca") int xKoniec,
//                                   @RequestParam(value = "y_konca") int yKoniec,
//                                   @RequestParam(value = "wysokosc") int wysokosc,
//                                   @RequestParam(value = "selectMagazyn") Magazyn magazyn,
//
//                                   @PathVariable Integer idRegal,
//                                   RedirectAttributes redirectAttributes) throws IOException {
//
//        Regal regal = regalServiceImpl.getOneById(idRegal);
//
//        regal.setIloscPolek(iloscPolek);
//        regal.setxPoczatek(xPoczatek);
//        regal.setyPoczatek(yPoczatek);
//        regal.setxKoniec(xKoniec);
//        regal.setyKoniec(yKoniec);
//        regal.setWysokosc(wysokosc);
//        regal.setMagazyn(magazyn);
//
//
//        regalServiceImpl.createRegal(regal);
//        // redirectAttributes.addFlashAttribute("success_msg", "Zmiany zostały przyjęte pomyślnie");
//        return "redirect:/regalyMagazynu/"+magazyn.getIdMagazyn();
//    }




}
