package com.example.Magazyn.service;


import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProduktServiceImpl implements ProduktService{

    @Autowired
    private ProduktRepository produktRepository;

    @Override
    public Produkt createProdukt(Produkt produkt) { return produktRepository.save(produkt);}

    @Override
    public List<Produkt> getAllProdukt() {return this.produktRepository.findAll(); }

    @Override
    public Produkt getOneById(Integer id) { return this.produktRepository.getOne(id); }

    @Override
    public void removeProdukt(Integer id) { this.produktRepository.deleteById(id); }



    @Override
    public int getAllByKategoria_idKategoria(Integer id) { return this.produktRepository.getAllProduktByKategoria_IdKategoria(id).size(); };




}
