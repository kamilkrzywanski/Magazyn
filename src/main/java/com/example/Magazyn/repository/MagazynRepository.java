package com.example.Magazyn.repository;

import com.example.Magazyn.model.Magazyn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MagazynRepository extends JpaRepository<Magazyn, Integer> {

    Magazyn findMagazynByNazwa(String magazynNazwa);

    List<Magazyn> findMagazynByNazwaIsLike(String magazynNazwa);


}
