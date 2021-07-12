package com.example.Magazyn.service;

import com.example.Magazyn.model.Uzytkownik;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UzytkownikService {

  
	Uzytkownik createUzytkownik(Uzytkownik uzytkownik) ;

    List<Uzytkownik> getAllUzytkownik();
    
    Uzytkownik getOneById(Integer id);

    void removeUzytkownik(Integer id);

    Uzytkownik getLoggedUserDetails(Authentication authentication);

}
