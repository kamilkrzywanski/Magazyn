package com.example.Magazyn.service;


import com.example.Magazyn.model.Kategoria;

import java.util.List;

public interface KategoriaService {

        List<Kategoria> getAllKategoria();

        Kategoria getOneById(Integer id);

        void createKategoria(Kategoria kategoria);

        void removeKategoria(Integer idKategoria);

        void updateKategoria (int idKategoria, String kategoria);


}
