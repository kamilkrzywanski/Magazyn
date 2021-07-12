package com.example.Magazyn.service;

import com.example.Magazyn.model.Rola;

import java.util.List;

public interface RolaService {

    List<Rola> getAllRola();

    Rola getOneById(Integer id);

    public Rola getOneByName(String name);


}
