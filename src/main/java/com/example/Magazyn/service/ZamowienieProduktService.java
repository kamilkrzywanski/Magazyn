package com.example.Magazyn.service;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.Zamowienie;
import com.example.Magazyn.model.ZamowienieProdukt;

import java.util.List;

public interface ZamowienieProduktService {


    ZamowienieProdukt createZamowienieProdukt(ZamowienieProdukt zamowienieProdukt);

    List<ZamowienieProdukt> getAllZamowienieProdukt();

    ZamowienieProdukt getOneById(Integer id);

    void removeZamowienieProdukt(Integer id);

    List<ZamowienieProdukt> getAllZamowienieProduktByIdZamowienie(int idZamowienie);

    List<ZamowienieProdukt> findAllByZamowienieIdZamowienieProduktIdProdukt(Produkt produkt, Zamowienie zamowienie);

    ZamowienieProdukt getOneByZamowienie_IdZamowienie_Produkt_IdProdukt(Produkt produkt, Zamowienie zamowienie);

    void updateIlosc(int idZamowienie, int idProdukt);

}
