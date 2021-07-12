package com.example.Magazyn.controller;

import com.example.Magazyn.model.Pracownik;
import com.example.Magazyn.model.Rola;
import com.example.Magazyn.model.Uzytkownik;
import com.example.Magazyn.service.PracownikServiceImpl;
import com.example.Magazyn.service.RolaServiceImpl;
import com.example.Magazyn.service.UzytkownikServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PracownikController {
    @Autowired // podłączamy Servicy z których bedzimy koszystać
    private PracownikServiceImpl pracownikServiceImpl;
    @Autowired
    private UzytkownikServiceImpl uzytkownikServiceImpl;
    @Autowired
    private RolaServiceImpl rolaServiceImpl;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/pracownicy") // ścieżka na której zostanie obsłużona metoda
    public String getAllPracownik(Model model, Authentication authentication) {

        model.addAttribute("pracownicy", pracownikServiceImpl.getAllPracownik());
        model.addAttribute("LoggedUser", authentication);

        return "pracownik/pracownicy";
    }

    @PostMapping("/kierownicy/dodaj")
    public String createKierownik(@RequestParam(value="imie") String imie,
                                  @RequestParam(value="nazwisko") String nazwisko,
                                  @RequestParam(value="telefon") String telefon,
                                  @RequestParam(value="login") String login,
                                  @RequestParam(value="haslo") String haslo,
                                  @RequestParam(value="email") String email,
                                   RedirectAttributes redirectAttributes,
                                   Authentication authentication) {

        Rola rola = rolaServiceImpl.getOneByName("ROLE_kierownik");
        Uzytkownik uzytkownik = new Uzytkownik(login, passwordEncoder.encode(haslo), email, rola);
        rola.getUzytkownicy().add(uzytkownik);
        Pracownik pracownik = new Pracownik(imie, nazwisko, telefon, uzytkownik);

        pracownikServiceImpl.createPracownik(pracownik);


        redirectAttributes.addFlashAttribute("success_msg", "Dodano wiersz pomyślnie!");

        if(authentication != null)
            return "redirect:/pracownicy";
        else
            return "redirect:/login";
    }

    @PostMapping("/pracownicy/dodaj")
    public String createPracownik(@RequestParam(value="imie") String imie,
                                  @RequestParam(value="nazwisko") String nazwisko,
                                  @RequestParam(value="telefon") String telefon,
                                  @RequestParam(value="login") String login,
                                  @RequestParam(value="haslo") String haslo,
                                  @RequestParam(value="email") String email,
                                  RedirectAttributes redirectAttributes,
                                  Authentication authentication) {

        Rola rola = rolaServiceImpl.getOneByName("ROLE_pracownik");
        Uzytkownik uzytkownik = new Uzytkownik(login, passwordEncoder.encode(haslo), email, rola);
        rola.getUzytkownicy().add(uzytkownik);
        Pracownik pracownik = new Pracownik(imie, nazwisko, telefon, uzytkownik);

        pracownikServiceImpl.createPracownik(pracownik);


        redirectAttributes.addFlashAttribute("success_msg", "Dodano wiersz pomyślnie!");

        if(authentication != null)
            return "redirect:/pracownicy";
        else
            return "redirect:/login";
    }

    @GetMapping("/pracownicy/usun/{idPracownik}")
    public String removePracownik(@PathVariable Integer idPracownik) {

        Pracownik pracownik = pracownikServiceImpl.getOneById(idPracownik);
        Uzytkownik uzytkownik = pracownik.getUzytkownik();
        pracownik.setUzytkownik(null);

        uzytkownikServiceImpl.removeUzytkownik(uzytkownik.getIdUzytkownik());

        return "redirect:/pracownicy";
    }

    @GetMapping({"/pracownicy/form", "/pracownicy/form", "/rejestracja/pracownik"})
    public String formPracownik(Model model,
                                 @RequestParam(value = "id", required = false) Integer idPracownik,
                                 Authentication authentication) {

        model.addAttribute("LoggedUser", authentication);

        if(idPracownik != null){
            Pracownik pracownik = pracownikServiceImpl.getOneById(idPracownik);
            model.addAttribute("pracownik", pracownik);
            model.addAttribute("update", "1");

        }

        model.addAttribute("kierownik", "0");

        return "pracownik/pracownicyForm";
    }

    @GetMapping({"/rejestracja/kierownik"})
    public String formKierownk(Model model,
                                @RequestParam(value = "id", required = false) Integer idPracownik,
                                Authentication authentication) {

        model.addAttribute("LoggedUser", authentication);

        if(idPracownik != null){
            Pracownik pracownik = pracownikServiceImpl.getOneById(idPracownik);
            model.addAttribute("pracownik", pracownik);
            model.addAttribute("update", "1");
            model.addAttribute("kierownik", "1");

        }
        model.addAttribute("kierownik", "1");

        return "pracownik/pracownicyForm";
    }



    @PostMapping("/pracownicy/update/{idPracownik}")
    public String updatePracownik(@RequestParam(value="imie") String imie,
                                 @RequestParam(value="nazwisko") String nazwisko,
                                 @RequestParam(value="telefon") String telefon,
                                 @RequestParam(value="login") String login,
                                 @RequestParam(value="haslo", required = false) String haslo,
                                 @RequestParam(value="email") String email,
                                 @PathVariable Integer idPracownik) {

        Pracownik pracownik = pracownikServiceImpl.getOneById(idPracownik);

        pracownik.setImie(imie);
        pracownik.setNazwisko(nazwisko);
        pracownik.setTelefon(telefon);
        pracownik.getUzytkownik().setLogin(login);
        if(haslo != "")
            pracownik.getUzytkownik().setHaslo(passwordEncoder.encode(haslo));
        pracownik.getUzytkownik().setEmail(email);

        pracownikServiceImpl.createPracownik(pracownik);

        return "redirect:/pracownicy";
    }
}
