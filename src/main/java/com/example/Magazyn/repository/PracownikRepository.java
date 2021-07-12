package com.example.Magazyn.repository;

import com.example.Magazyn.model.Pracownik;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PracownikRepository extends JpaRepository<Pracownik, Integer> {

    @Modifying
    @Query("delete from Pracownik p where p.idPracownik = ?1")
    public void deletePracownik(Integer idPracownik);

}
