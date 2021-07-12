package com.example.Magazyn.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Produkt")
public class Produkt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produkt")
    private int idProdukt;

    @Column(name = "nazwa", nullable=true, length=100)
    private String nazwa;

    @Column(name = "stan_magazynowy")
    private int stanMagazynowy;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_kategoria", referencedColumnName = "id_kategoria")
    private Kategoria kategoria;


    @Column(name = "dlugosc")
    private int dlugosc;

    @Column(name = "szerokosc")
    private int szerokosc;

    @Column(name = "wysokosc")
    private int wysokosc;



    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produkt", referencedColumnName = "id_produkt")
    private List<ZamowienieProdukt> zamowienieProdukt = new ArrayList<ZamowienieProdukt>();

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_produkt", referencedColumnName = "id_produkt")
    private List<ProduktRegal> produktRegal = new ArrayList<ProduktRegal>();

    public Produkt() {
    }

    public Produkt(String nazwa, int stanMagazynowy, Kategoria kategoria, int dlugosc, int szerokosc, int wysokosc) {
        this.nazwa = nazwa;
        this.stanMagazynowy = stanMagazynowy;
        this.kategoria = kategoria;
        this.dlugosc = dlugosc;
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
    }

    public int getIdProdukt() {
        return idProdukt;
    }



    public String getNazwa() {
        return nazwa;
    }

    public int getStanMagazynowy() {
        return stanMagazynowy;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }


    public int getDlugosc() {
        return dlugosc;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public List<ZamowienieProdukt> getZamowienieProdukt() {
        return zamowienieProdukt;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public void setStanMagazynowy(int stanMagazynowy) {
        this.stanMagazynowy = stanMagazynowy;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }


    public void setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
    }

    public void setSzerokosc(int szerokosc) {
        this.szerokosc = szerokosc;
    }

    public void setWysokosc(int wysokosc) {
        this.wysokosc = wysokosc;
    }

    public void setZamowienieProdukt(List<ZamowienieProdukt> zamowienieProdukt) {
        this.zamowienieProdukt = zamowienieProdukt;
    }



}
