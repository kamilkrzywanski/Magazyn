package com.example.Magazyn.controller;

import com.example.Magazyn.model.*;
import com.example.Magazyn.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ZamowienieController {

    @Autowired
    private ZamowienieServiceImpl zamowienieServiceImpl;

    @Autowired
    private ProduktServiceImpl produktServiceImpl;

    @Autowired
    private PracownikServiceImpl pracownikServiceImpl;

    @Autowired
    private MagazynServiceImpl magazynServiceImpl;

    @Autowired
    private RegalServiceImpl regalServiceImpl;

    @Autowired
    private ProduktRegalServiceImpl produktRegalServiceImpl;

    @Autowired
    private ZamowienieProduktServiceImpl zamowienieProduktServiceImpl;


    @GetMapping("/zamowienia")
    public ModelAndView getAllZamowienie(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("zamowienia/zamowienia");
        modelAndView.addObject("zamowienia", zamowienieServiceImpl.getAllZamowienie());
        modelAndView.addObject("LoggedUser", authentication);


        return modelAndView;
    }

    @GetMapping("/zamowienia/historia")
    public ModelAndView zamowieniaHistoria(Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("zamowienia/zamowieniaHistoria");
        modelAndView.addObject("zamowienia", zamowienieServiceImpl.getAllZamowienie());
        modelAndView.addObject("LoggedUser", authentication);


        return modelAndView;
    }


    @GetMapping("/zamowienia/addForm")
    public ModelAndView addFormZamowienie(Authentication authentication)
    {
        ModelAndView modelAndView = new ModelAndView("/zamowienia/addForm");
        modelAndView.addObject("zamowienia", zamowienieServiceImpl.getAllZamowienie());
        modelAndView.addObject("pracownicy", pracownikServiceImpl.getAllPracownik());
        modelAndView.addObject("magazyny", magazynServiceImpl.getAllMagazyn());
        modelAndView.addObject("LoggedUser", authentication);
        return modelAndView;
    }


    @PostMapping("/zamowienia/dodaj")
    public String addZamowienie(@RequestParam(value = "adres") String adres,
                                @RequestParam(value = "selectPracownik") Pracownik pracownik,
                                @RequestParam(value = "data_zlozenia") Date dataZlozenia,
                                @RequestParam(value = "termin_realizacji") Date terminRealizacji,
                                @RequestParam(value = "selectMagazyn") Magazyn magazyn,

                             HttpServletRequest request,
                             RedirectAttributes redirectAttributes)
    {
        Zamowienie zamowienie = new Zamowienie(adres, pracownik,dataZlozenia,terminRealizacji, magazyn);
        zamowienieServiceImpl.createZamowienie(zamowienie);
        redirectAttributes.addFlashAttribute("wiadomosc", "Dodano zamowienie");

        return "redirect:/zamowienia";
    }





    @GetMapping("/zamowienia/updateForm")
    public String updateFormZamowienie(@RequestParam(value = "id") Integer idZamowienie,
                                    Model model,
                                    Authentication authentication) {

        model.addAttribute("LoggedUser", authentication);

        Zamowienie zamowienie = zamowienieServiceImpl.getOneById(idZamowienie);
        Pracownik pracownik = zamowienie.getPracownik();
        model.addAttribute("zamowienie", zamowienie);
        model.addAttribute("pracownik", pracownik);
        model.addAttribute("pracownicy", pracownikServiceImpl.getAllPracownik());


        return "zamowienia/updateForm";
    }


    @PostMapping("/zamowienia/update/{idZamowienie}")
    public String updateZamowienie(@RequestParam(value = "adres") String adres,
                                   @RequestParam(value = "selectPracownik") Pracownik pracownik,
                                   @RequestParam(value = "data_zlozenia") Date dataZlozenia,
                                   @RequestParam(value = "termin_realizacji") Date terminRealizacji,
                                @PathVariable Integer idZamowienie,
                                RedirectAttributes redirectAttributes) throws IOException {

        Zamowienie zamowienie = zamowienieServiceImpl.getOneById(idZamowienie);

        zamowienie.setAdres(adres);
        zamowienie.setPracownik(pracownik);
        zamowienie.setDataZlozenia(dataZlozenia);
        zamowienie.setTerminRealizacji(terminRealizacji);


        zamowienieServiceImpl.createZamowienie(zamowienie);
        redirectAttributes.addFlashAttribute("success_msg", "Zmiany zostały przyjęte pomyślnie");
        return "redirect:/zamowienia";
    }

    @GetMapping("/zamowienia/wyznaczTrase/{idZamowienie}")
    public ModelAndView wyznaczTrase(@PathVariable int idZamowienie ,
                                       Model model,
                                       Authentication authentication,
                                     RedirectAttributes redirectAttributes) {

        model.addAttribute("LoggedUser", authentication);

        Zamowienie zam = zamowienieServiceImpl.getOneById(idZamowienie);
        Magazyn mag = zam.getMagazyn();
        List<Regal> regalList = regalServiceImpl.getAllRegalByIdMagazyn(mag.getIdMagazyn());


        List<ZamowienieProdukt> produkts = zamowienieProduktServiceImpl.getAllZamowienieProduktByIdZamowienie(idZamowienie);
        List<ProduktRegal> produktRegals = new ArrayList<>();


        List<Punkt> punktyFalse = new ArrayList<>();


        for (int i = 0; i < produkts.size(); i++) {
            produktRegals.addAll(produktRegalServiceImpl.findTopXProduktowRegals(produkts.get(i).getProdukt().getIdProdukt(), mag.getIdMagazyn(), produkts.get(i).getIlosc()));

        if(produkts.get(i).getProdukt().getStanMagazynowy()< produkts.get(i).getIlosc())
                {

                    redirectAttributes.addFlashAttribute("error_msg", "Nie można zrealizować zamówienia, brak"+" " +(  produkts.get(i).getIlosc() - produkts.get(i).getProdukt().getStanMagazynowy()) +"x " +produkts.get(i).getProdukt().getNazwa()  );
                         ModelAndView modelAndView = new ModelAndView("redirect:/zamowieniaProdukt/"+idZamowienie);


                    return modelAndView;

                }


        }

        List<ProduktRegal> produktRegalsTmp = new ArrayList<>();

        produktRegalsTmp.addAll(produktRegals);
        ///Utworzenie tablicy list, z drogami po produkty

        List<Punkt> punktyDrogi = new ArrayList<Punkt>();
        List<ProduktRegal>  produktRegalDroga = new ArrayList<ProduktRegal>();

        ///Wyznaczenie pól po których pracownik może się przemieszczać (Pola nie zajęte przez regały)
        boolean[][] pola = new boolean[mag.getSzerokosc() * 10][mag.getDlugosc() * 10];

        for (int i = 0; i < mag.getSzerokosc() * 10; i++)
        {
            for(int j = 0 ; j< mag.getDlugosc()*10; j++)
                pola[i][j] = true;

        }


       for(int i = 0; i<regalList.size(); i++)
       {
       Regal tmp =  regalList.get(i);

        for (int j = tmp.getxPoczatek()/10; j < tmp.getxPoczatek()/10 +  tmp.getSzerokosc()/10;  j++ )
        {
            {
                for (int k = tmp.getyPoczatek()/10; k < tmp.getyPoczatek()/10 + tmp.getDlugosc()/10; k++)
                {
                    pola[j][k] = false;
                    punktyFalse.add(new Punkt(j,k,0));
                }
            }
        }
       }

        Siatka siatka =  new Siatka (mag.getSzerokosc()*10, mag.getDlugosc()*10, pola);

      List <Punkt> start  = new ArrayList<>();
      start.add(new Punkt(mag.getxWejscie()/10, mag.getyWejscie()/10, 0));


        Punkt strt = new Punkt();
        Punkt trgt = new Punkt();



        List <Punkt> droga = new ArrayList();
        int distance = Integer.MAX_VALUE;
        int indexS = 0;
        int indexT = 0;
        strt = new Punkt( mag.getxWejscie()/10, mag.getyWejscie()/10, 0     );



        ///Licznik do tablicy punktów
        int licznikTabeli = 0;
       while (produktRegals.size() > 0)
       {

        for(int i = 0; i < produktRegals.size() ; i++)
        {

        trgt = new Punkt(produktRegals.get(i).getxProduktu()/10-1, produktRegals.get(i).getyProduktu()/10-1, produktRegals.get(i).getIdProduktRegal());

        if(Wyszukiwarka.znajdzDroge(siatka, strt, trgt,true ).size() < distance  )
        {
            distance = Wyszukiwarka.znajdzDroge(siatka, strt, trgt,true ).size();
            indexS = strt.getIndex();
            indexT = i;

        }

        }

        trgt = new Punkt(produktRegals.get(indexT).getxProduktu()/10-1, produktRegals.get(indexT).getyProduktu()/10-1, produktRegals.get(indexT).getIdProduktRegal());
           droga.add(new Punkt( Wyszukiwarka.znajdzDroge(siatka, strt, trgt,true ).size(),strt.getIndex(), trgt.getIndex()    ));

          punktyDrogi.addAll(Wyszukiwarka.znajdzDroge(siatka, strt, trgt,true ));
           produktRegalDroga.add(produktRegalServiceImpl.getOneById(trgt.getIndex()) );

           licznikTabeli++;


        strt = trgt;
        distance = Integer.MAX_VALUE;

        for(int i = 0; i < produktRegals.size(); i++){
            if(produktRegals.get(i).getIdProduktRegal() == trgt.getIndex())
            {
                produktRegals.remove(i);
                indexS = indexT;
            }
        }
       }

       trgt = new Punkt( mag.getxWejscie()/10, mag.getyWejscie()/10, 0     );

        droga.add(new Punkt( Wyszukiwarka.znajdzDroge(siatka, strt, trgt,true ).size(),strt.getIndex(), trgt.getIndex()    ));

        punktyDrogi.addAll(Wyszukiwarka.znajdzDroge(siatka, strt, trgt,true ));

        List<ProduktRegal> poprawnaKolejnoscDrogi = new ArrayList<ProduktRegal>();
        for(int i = 0; i < droga.size(); i++)
        {
            if(droga.get(i).getIndex() != 0)
        poprawnaKolejnoscDrogi.add(produktRegalServiceImpl.getOneById(droga.get(i).getIndex()));


        }

        Zamowienie zamowienie = zamowienieServiceImpl.getOneById(idZamowienie);
        Pracownik pracownik = zamowienie.getPracownik();

        ModelAndView modelAndView = new ModelAndView("/zamowienieProdukty/zamowienieProduktyTrasa");



        modelAndView.addObject("produkty", poprawnaKolejnoscDrogi);
        modelAndView.addObject("produktregal", start.size());


        ///modelAndView.addObject("start", distances);
        modelAndView.addObject("target", droga);


        modelAndView.addObject("magazyn", magazynServiceImpl.getOneById(mag.getIdMagazyn()));
        modelAndView.addObject("regaly", regalServiceImpl.getAllRegalByIdMagazyn(mag.getIdMagazyn()));
        modelAndView.addObject("punkty", punktyDrogi);



        modelAndView.addObject("zamowienie", zamowienie);
        modelAndView.addObject("pracownik", pracownik);
        modelAndView.addObject("pracownicy", pracownikServiceImpl.getAllPracownik());

        return modelAndView;


    }


    @GetMapping("/zamowienia/zrealizuj/{idZamowienie}")
    public ModelAndView zrealizujZamowienie(@PathVariable int idZamowienie,

                                            Model model,
                                            Authentication authentication,
                                            RedirectAttributes redirectAttributes)
    {
        Zamowienie zam = zamowienieServiceImpl.getOneById(idZamowienie);
        Magazyn mag = zam.getMagazyn();
        List<ZamowienieProdukt> produkts = zamowienieProduktServiceImpl.getAllZamowienieProduktByIdZamowienie(idZamowienie);
        List<ProduktRegal> produktRegals = new ArrayList<>();
        for(int i = 0; i<produkts.size(); i++)
        {
            produktRegals.addAll(produktRegalServiceImpl.findTopXProduktowRegals(produkts.get(i).getProdukt().getIdProdukt(), mag.getIdMagazyn(), produkts.get(i).getIlosc()));

        }
        for(int j = 0; j<produktRegals.size(); j++)
        {
            int stanMagazynowy =    produktServiceImpl.getOneById(produktRegals.get(j).getIdProdukt()).getStanMagazynowy()-1;

            produktServiceImpl.getOneById(produktRegals.get(j).getIdProdukt()).setStanMagazynowy(stanMagazynowy);
            produktRegalServiceImpl.removeProduktRegal(produktRegals.get(j).getIdProduktRegal());

        }



        zamowienieServiceImpl.updateStatus(idZamowienie, 1);
        redirectAttributes.addFlashAttribute("wiadomosc", "Zamowienie zrealizowane");
        ModelAndView modelAndView = new ModelAndView("redirect:/zamowienia/");

        return modelAndView;
    }


    @InitBinder
    protected void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, null,  new CustomDateEditor(dateFormat, true));
    }
}
