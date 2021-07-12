package com.example.Magazyn.service;

import com.example.Magazyn.model.Regal;

import java.util.List;


public interface RegalService {

    Regal createRegal(Regal regal);

    List<Regal> getAllRegal();

    Regal getOneById(Integer id);

    void removeRegal(Integer id);

    List<Regal> getAllRegalByIdMagazyn(int idMagazyn);

}
