package com.example.Magazyn.service;

import com.example.Magazyn.model.Pracownik;

import java.util.List;

public interface PracownikService {

    Pracownik createPracownik(Pracownik pracownik);

    List<Pracownik> getAllPracownik();

    Pracownik getOneById(Integer id);

    void removePracownik(Integer id);

    void deletePracownikQuery(Integer id);



    //List<Pracownik> getPracownikUser();

}
