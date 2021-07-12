package com.example.Magazyn.service;

import com.example.Magazyn.model.Pracownik;
import com.example.Magazyn.repository.PracownikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PracownikServiceImpl implements PracownikService{ // service obsługuje nasze metody z repozytorium

    @Autowired
    private PracownikRepository pracownikRepository;

    @Override
    public Pracownik createPracownik(Pracownik pracownik) { return pracownikRepository.save(pracownik);}

    @Override
    public List<Pracownik> getAllPracownik() {return this.pracownikRepository.findAll(); }

    @Override
    public Pracownik getOneById(Integer id) { return this.pracownikRepository.getOne(id); }

    @Override
    public void removePracownik(Integer id) { this.pracownikRepository.deleteById(id); }

    @Override
    public void deletePracownikQuery(Integer id) { this.pracownikRepository.deletePracownik(id); }



    //public List<Pracownik> getPracownikUser(){ return this.pracownikRepository.findAllPracownikUser(); }
}
