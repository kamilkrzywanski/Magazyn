package com.example.Magazyn.service;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.Zamowienie;
import com.example.Magazyn.repository.ZamowienieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ZamowienieServiceImpl implements  ZamowienieService{

    @Autowired
    ZamowienieRepository zamowienieRepository;

    @Override
    public Zamowienie createZamowienie(Zamowienie zamowienie) { return zamowienieRepository.save(zamowienie); }

    @Override
    public List<Zamowienie> getAllZamowienie() { return this.zamowienieRepository.findAll();  }

    @Override
    public void removeZamowienie(Integer id) { this.zamowienieRepository.deleteById(id); }

    @Override
    public Zamowienie getOneById(Integer id) {return this.zamowienieRepository.getOne(id);}

    @Override
    public List<Zamowienie> getAllZamowienieByIdPracownik(int idPracownik){
        List<Zamowienie> zamowienie = this.zamowienieRepository.findAllByPracownik_IdPracownik(idPracownik);
        return zamowienie;
    }

    @Override
    public void updateStatus(int idZamownie, int status) { this.getOneById(idZamownie).setStatus(status);      }



}
