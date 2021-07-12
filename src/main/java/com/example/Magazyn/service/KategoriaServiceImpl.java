package com.example.Magazyn.service;


import com.example.Magazyn.model.Kategoria;
import com.example.Magazyn.repository.KategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class KategoriaServiceImpl implements KategoriaService {

    @Override
    public List<Kategoria> getAllKategoria() { return this.kategoriaRepository.findAll();}

    @Autowired
    private KategoriaRepository kategoriaRepository;

    @Override
    public void createKategoria(Kategoria kategoria)  {
        kategoriaRepository.save(kategoria);
    }

    @Override
    public Kategoria getOneById(Integer id) {
        return this.kategoriaRepository.getOne(id);
    }



    @Override
    public void removeKategoria(Integer idKategoria) {
        this.kategoriaRepository.deleteById(idKategoria);
    }

    @Override
    public void updateKategoria(int iKategoria, String kategoria) { this.getOneById(iKategoria).setNazwa(kategoria);      }
}
