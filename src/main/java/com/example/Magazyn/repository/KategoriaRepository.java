package com.example.Magazyn.repository;

import com.example.Magazyn.model.Kategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KategoriaRepository extends JpaRepository<Kategoria, Integer> {


}
