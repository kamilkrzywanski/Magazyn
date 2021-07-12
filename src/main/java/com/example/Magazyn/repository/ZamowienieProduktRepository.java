package com.example.Magazyn.repository;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.Zamowienie;
import com.example.Magazyn.model.ZamowienieProdukt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZamowienieProduktRepository extends JpaRepository<ZamowienieProdukt, Integer> {

    List<ZamowienieProdukt> findAllByZamowienie_IdZamowienie(int idZamowienie);

    List<ZamowienieProdukt> findAllByProduktAndZamowienie(Produkt produkt, Zamowienie zamowienie);

    ZamowienieProdukt findTopByProduktAndZamowienie(Produkt produkt, Zamowienie zamowienie);


}
