package com.example.Magazyn.repository;


import com.example.Magazyn.model.Produkt;
import com.example.Magazyn.model.ProduktRegal;
import com.example.Magazyn.model.Regal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProduktRegalRepository extends JpaRepository<ProduktRegal, Integer> {


    List<ProduktRegal> getAllByRegal_IdRegal(int idRegal);

    ProduktRegal findTopProduktRegalByProdukt_IdProdukt (int IdProdukt);

    ProduktRegal findTopProduktByProdukt_IdProduktAndRegal_Magazyn_IdMagazyn (int IdProdukt, int IdMagazyn);


    @Query(value = "SELECT DISTINCT TOP (:ilosc) produkt_regal.* from dbo.produkt_regal \n" +
            "inner join produkt \n" +
            "on produkt_regal.id_produkt = :idprodukt\n"+
            "inner join magazyn\n" +
            "on magazyn.id_magazyn = :idmagazynu\n", nativeQuery = true)
    List<ProduktRegal> findTopXProduktowRegal (@Param("idprodukt")Integer idprodukt ,@Param("idmagazynu")Integer idmagazynu ,@Param("ilosc")Integer ilosc);



    @Query(value = "SELECT  DISTINCT id_produkt from dbo.produkt_regal\n" +
            "INNER JOIN regal\n" +
            "ON produkt_regal.id_regal = regal.id_regal\n" +
            "INNER JOIN magazyn\n" +
            "ON regal.id_magazyn  = :idMagazyn", nativeQuery = true)
    List<Integer> getAllProduktyWMagazynie(@Param("idMagazyn")Integer idMagazyn);







}
