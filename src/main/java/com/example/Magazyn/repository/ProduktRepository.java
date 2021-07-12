package com.example.Magazyn.repository;

import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.ProduktRegal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Integer> {


    List<Produkt> getAllProduktByKategoria_IdKategoria (int IdKategoria);



}
