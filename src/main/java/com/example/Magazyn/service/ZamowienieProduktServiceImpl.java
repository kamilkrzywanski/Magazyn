package com.example.Magazyn.service;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.Zamowienie;
import com.example.Magazyn.model.ZamowienieProdukt;
import com.example.Magazyn.repository.ZamowienieProduktRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ZamowienieProduktServiceImpl implements ZamowienieProduktService {

    @Autowired
    private ZamowienieProduktRepository zamowienieProduktRepository;

    @Override
    public ZamowienieProdukt createZamowienieProdukt(ZamowienieProdukt zamowienieProdukt) { return zamowienieProduktRepository.save(zamowienieProdukt);}

    @Override
    public List<ZamowienieProdukt> getAllZamowienieProdukt() {return this.zamowienieProduktRepository.findAll(); }

    @Override
    public ZamowienieProdukt getOneById(Integer id) { return this.zamowienieProduktRepository.getOne(id); }

    @Override
    public void removeZamowienieProdukt(Integer id) { this.zamowienieProduktRepository.deleteById(id); }

    @Override
    public List<ZamowienieProdukt> getAllZamowienieProduktByIdZamowienie(int idZamowienie){
        List<ZamowienieProdukt> zamowienieProdukt = this.zamowienieProduktRepository.findAllByZamowienie_IdZamowienie(idZamowienie);
        return zamowienieProdukt;
    }

    @Override
    public List<ZamowienieProdukt> findAllByZamowienieIdZamowienieProduktIdProdukt(Produkt produkt, Zamowienie zamowienie){

        return zamowienieProduktRepository.findAllByProduktAndZamowienie(produkt, zamowienie);

    }

    @Override
    public ZamowienieProdukt getOneByZamowienie_IdZamowienie_Produkt_IdProdukt( Produkt produkt, Zamowienie zamowienie){

        return zamowienieProduktRepository.findTopByProduktAndZamowienie(produkt,zamowienie);
    }

    @Override
     public void updateIlosc(int idZamowienieProdukt, int ilosc){

         zamowienieProduktRepository.getOne(idZamowienieProdukt).setIlosc(ilosc);

    }




}
