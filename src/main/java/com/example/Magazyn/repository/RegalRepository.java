package com.example.Magazyn.repository;

import com.example.Magazyn.model.Regal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface RegalRepository extends JpaRepository<Regal, Integer> {

    List<Regal> findAllByMagazyn_IdMagazyn(int idGrupa);


}
