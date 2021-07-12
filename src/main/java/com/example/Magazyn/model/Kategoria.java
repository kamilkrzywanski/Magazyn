package com.example.Magazyn.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Kategoria")
public class Kategoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kategoria")
    private int idKategoria;

    @Column(name = "nazwa", nullable=true, length=255)
    private String nazwa;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_kategoria", referencedColumnName = "id_kategoria")
    private List<Produkt> produkt = new ArrayList<Produkt>();


    public Kategoria() {
    }

    public Kategoria(String nazwa) {
        this.nazwa = nazwa;
    }

    public int getIdKategoria() {
        return idKategoria;
    }

    public String getNazwa() {
        return nazwa;
    }

    public List<Produkt> getProdukt() {
        return produkt;
    }


    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
