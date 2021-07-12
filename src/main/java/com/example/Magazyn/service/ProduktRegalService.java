package com.example.Magazyn.service;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.ProduktRegal;
import com.example.Magazyn.model.Regal;

import java.util.List;

public interface ProduktRegalService {


    ProduktRegal createProduktRegal(ProduktRegal produktRegal);

    List<ProduktRegal> getAllProduktRegal();

    ProduktRegal getOneById(Integer id);

    void removeProduktRegal(Integer id);

    List<ProduktRegal> getAllProduktRegalByIdRegal(int idRegal);

    ProduktRegal findTopProduktByIdProdukt(int idProdukt);

    ProduktRegal findTopProduktByProdukt_IdProduktAndRegal_Magazyn_IdMagazyn(int idProdukt, int idMagazyn);

    List<ProduktRegal> findTopXProduktowRegals(int idProdukt, int idMagazyn, int ilosc);

    List<Integer> getAllProduktyWMagazynieId (int IdMagazyn);

}
