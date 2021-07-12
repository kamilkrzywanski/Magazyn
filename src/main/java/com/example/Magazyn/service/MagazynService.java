package com.example.Magazyn.service;

import com.example.Magazyn.model.Magazyn;

import java.util.List;

public interface MagazynService {

    List<Magazyn> getAllMagazyn();

    Magazyn getOneById(Integer id);

    Magazyn getOneByName(String name);

    Magazyn createMagazyn(Magazyn magazyn);

    void deleteMagazyn(Integer id);


}
