package com.example.Magazyn.service;



import java.util.List;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.Zamowienie;


public interface ZamowienieService {

    Zamowienie createZamowienie(Zamowienie zamowienie) ;

    List<Zamowienie> getAllZamowienie();

    Zamowienie getOneById(Integer id);

    void removeZamowienie(Integer id);

    List<Zamowienie> getAllZamowienieByIdPracownik(int idPracownik);

    void updateStatus (int IdZamowieie, int status);

}
