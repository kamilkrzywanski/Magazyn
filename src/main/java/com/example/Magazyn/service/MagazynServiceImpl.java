package com.example.Magazyn.service;


import com.example.Magazyn.model.Magazyn;
import com.example.Magazyn.repository.MagazynRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class MagazynServiceImpl implements MagazynService  {

    @Autowired
    private MagazynRepository magazynRepository;

    @Override
    public List<Magazyn> getAllMagazyn() { return this.magazynRepository.findAll();}

    @Override
    public Magazyn getOneById(Integer id) { return this.magazynRepository.getOne(id); }

    @Override
    public Magazyn getOneByName(String name) { return this.magazynRepository.findMagazynByNazwa(name); }

    @Override
    public Magazyn createMagazyn(Magazyn grupa) {return this.magazynRepository.save(grupa);}

    @Override
    public void deleteMagazyn(Integer id) { this.magazynRepository.deleteById(id);	}




}
