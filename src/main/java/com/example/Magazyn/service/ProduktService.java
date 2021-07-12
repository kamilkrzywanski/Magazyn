package com.example.Magazyn.service;

import com.example.Magazyn.model.Produkt;

import java.util.List;

public interface ProduktService {

    Produkt createProdukt(Produkt produkt);

    List<Produkt> getAllProdukt();

    Produkt getOneById(Integer id);

    void removeProdukt(Integer id);

    int getAllByKategoria_idKategoria(Integer idKategoria);



}
