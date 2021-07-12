package com.example.Magazyn.repository;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.Zamowienie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ZamowienieRepository extends JpaRepository<Zamowienie, Integer> {

    List<Zamowienie> findAllByPracownik_IdPracownik(int idPracownik);


}
