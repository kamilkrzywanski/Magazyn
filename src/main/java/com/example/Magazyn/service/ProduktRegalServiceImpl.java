package com.example.Magazyn.service;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.ProduktRegal;
import com.example.Magazyn.model.Regal;
import com.example.Magazyn.repository.ProduktRegalRepository;
import com.example.Magazyn.repository.ProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProduktRegalServiceImpl implements ProduktRegalService {

    @Autowired
    ProduktRegalRepository produktRegalRepository;

    @Autowired
    ProduktRepository produktRepository;

    @Override
    public ProduktRegal createProduktRegal(ProduktRegal produktRegal) {
        return produktRegalRepository.save(produktRegal);
    }

    @Override
    public List<ProduktRegal> getAllProduktRegal() {
        return this.produktRegalRepository.findAll();
    }

    @Override
    public ProduktRegal getOneById(Integer id) {
        return this.produktRegalRepository.getOne(id);
    }

    @Override
    public void removeProduktRegal(Integer id) {
        this.produktRegalRepository.deleteById(id);
    }

    @Override
    public List<ProduktRegal> getAllProduktRegalByIdRegal(int idRegal) {

        return produktRegalRepository.getAllByRegal_IdRegal(idRegal);
    }


    @Override
    public ProduktRegal findTopProduktByIdProdukt(int idProdukt) {

        return produktRegalRepository.findTopProduktRegalByProdukt_IdProdukt(idProdukt);
    }


    @Override
    public ProduktRegal findTopProduktByProdukt_IdProduktAndRegal_Magazyn_IdMagazyn(int idProdukt, int idMagazyn) {

        return produktRegalRepository.findTopProduktByProdukt_IdProduktAndRegal_Magazyn_IdMagazyn(idProdukt, idMagazyn);
    }

    @Override
    public List<ProduktRegal> findTopXProduktowRegals(int idProdukt, int idMagazyn, int ilosc){

        return produktRegalRepository.findTopXProduktowRegal(idProdukt,idMagazyn,  ilosc);

    }


    @Override
    public List<Integer> getAllProduktyWMagazynieId(int IdMagazyn) {

        List<Integer> lista = produktRegalRepository.getAllProduktyWMagazynie(IdMagazyn);

        List<ProduktRegal> produktRegals = new ArrayList<>();


        for (Integer temp : lista) {
            produktRegals.add(produktRegalRepository.getOne(temp));
        }

            return lista;

            //return produktRegalRepository.getAllProduktyWMagazynie(IdMagazyn);
        }


    }
