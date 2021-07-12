package com.example.Magazyn.service;


import com.example.Magazyn.model.Regal;
import com.example.Magazyn.repository.RegalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RegalServiceImpl implements RegalService {

    @Autowired
    private RegalRepository regalRepository;

    @Override
    public Regal createRegal(Regal regal) { return regalRepository.save(regal);}

    @Override
    public List<Regal> getAllRegal() {return this.regalRepository.findAll(); }

    @Override
    public Regal getOneById(Integer id) { return this.regalRepository.getOne(id); }

    @Override
    public void removeRegal(Integer id) { this.regalRepository.deleteById(id); }

    @Override
    public List<Regal> getAllRegalByIdMagazyn(int idMagazyn){
        List<Regal> regal = this.regalRepository.findAllByMagazyn_IdMagazyn(idMagazyn);
        return regal;
    }

}
