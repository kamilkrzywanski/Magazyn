package com.example.Magazyn.controller;


import com.example.Magazyn.model.*;
import com.example.Magazyn.service.ProduktRegalServiceImpl;
import com.example.Magazyn.service.ProduktServiceImpl;
import com.example.Magazyn.service.ZamowienieProduktServiceImpl;
import com.example.Magazyn.service.ZamowienieServiceImpl;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller

public class ZamowienieProduktControler {

    @Autowired
    ZamowienieProduktServiceImpl zamowienieProduktServiceImpl;

    @Autowired
    ProduktServiceImpl produktServiceImpl;

    @Autowired
    ProduktRegalServiceImpl produktRegalServiceImpl;

    @Autowired
    ZamowienieServiceImpl zamowienieServiceImpl;

    @GetMapping("/zamowieniaProdukt/{idZamowienie}")
    public ModelAndView getAllZamowienieProduktForZamowienie(@PathVariable int idZamowienie, Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("zamowienieProdukty/zamowienieProdukty");
        modelAndView.addObject("zamowienieProdukty", zamowienieProduktServiceImpl.getAllZamowienieProduktByIdZamowienie(idZamowienie));
        modelAndView.addObject("zamowienie", zamowienieServiceImpl.getOneById(idZamowienie));
        modelAndView.addObject("idZamowienie", idZamowienie);
        modelAndView.addObject("LoggedUser", authentication);

        return modelAndView;
    }




    @GetMapping("/zamowienieProdukty/addForm/{idZamowienie}")
    public ModelAndView addFormProduktZamowienie(Authentication authentication,  @PathVariable Integer idZamowienie )
    {

//        int idMagazyn = zamowienieServiceImpl.getOneById(idZamowienie).getMagazyn().getIdMagazyn();
//
//        List<Integer> idProdukt = produktRegalServiceImpl.getAllProduktyWMagazynieId(idMagazyn);
//        List<Produkt> produkty = new ArrayList<>();
//
//        for(int i = 0; i < idProdukt.size(); i++ )
//        {
//            produkty.add(produktServiceImpl.getOneById(idProdukt.get(i)));
//
//        }

        List<Produkt> produkty = new ArrayList<>();
        produkty = produktServiceImpl.getAllProdukt();

        ModelAndView modelAndView = new ModelAndView("/zamowienieProdukty/addForm");

        
        modelAndView.addObject("produkty", produkty);

        //modelAndView.addObject("produkty", produktServiceImpl.getAllProdukt());
        modelAndView.addObject("idZamowienie", idZamowienie);

        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


    @PostMapping("/zamowienieProdukty/dodaj")
    public String addProduktRegal(@RequestParam(value = "idProdukt") Produkt idProdukt,
                                  @RequestParam(value = "idZamowienie") Zamowienie idZamowienie,
                                  @RequestParam(value = "ilosc") int ilosc,


                                  HttpServletRequest request,
                                  RedirectAttributes redirectAttributes)
    {
        ZamowienieProdukt zamowienieProdukt = new ZamowienieProdukt(idZamowienie,idProdukt,ilosc);
        if(zamowienieProduktServiceImpl.findAllByZamowienieIdZamowienieProduktIdProdukt( idProdukt,  idZamowienie).isEmpty() )
        zamowienieProduktServiceImpl.createZamowienieProdukt(zamowienieProdukt);
        else
        {
         ZamowienieProdukt zam =    zamowienieProduktServiceImpl.getOneByZamowienie_IdZamowienie_Produkt_IdProdukt(idProdukt, idZamowienie);


         ilosc+= zam.getIlosc();
         zamowienieProduktServiceImpl.updateIlosc(zam.getIdZamowienieProdukt(), ilosc);

        }



        redirectAttributes.addFlashAttribute("wiadomosc", "Dodano produkt");

        return "redirect:/zamowieniaProdukt/"+idZamowienie.getIdZamowienie();
    }

    @GetMapping("/zamowieniaProdukt/usun/{idZamowienieProdukt}")
    public String removeZamowienieProdukt(@PathVariable Integer idZamowienieProdukt,
                                RedirectAttributes redirectAttributes) {


         int idZamowienie =    zamowienieProduktServiceImpl.getOneById(idZamowienieProdukt).getZamowienie().getIdZamowienie();
           zamowienieProduktServiceImpl.removeZamowienieProdukt(idZamowienieProdukt);
            redirectAttributes.addFlashAttribute("success_msg", "Usunięto wiersz pomyślnie!");


        return "redirect:/zamowieniaProdukt/"+idZamowienie;
    }


}
