package com.example.Magazyn.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Regal")
public class Regal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regal")
    private int idRegal;

    @Column(name = "x_poczatek")
    private int xPoczatek;


    @Column(name = "y_poczatek")
    private int yPoczatek;



    @Column(name = "ilosc_polek")
    private int iloscPolek;

    @Column(name = "wysokosc")
    private int wysokosc;

    @Column(name = "szerokosc")
    private int szerokosc;

    @Column(name = "dlugosc")
    private int dlugosc;


    @ManyToOne
    @JoinColumn(name = "id_magazyn", referencedColumnName = "id_magazyn")
    private Magazyn magazyn;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_regal", referencedColumnName = "id_regal")
    private List<ProduktRegal> produktregal = new ArrayList<ProduktRegal>();


    public Regal() {
    }

    public Regal(int xPoczatek, int yPoczatek, int szerokosc,int dlugosc, int iloscPolek, int wysokosc,  Magazyn magazyn) {
        this.xPoczatek = xPoczatek;
        this.yPoczatek = yPoczatek;
        this.szerokosc = szerokosc;
        this.dlugosc = dlugosc;
        this.iloscPolek = iloscPolek;
        this.wysokosc = wysokosc;
        this.magazyn = magazyn;


    }


    public int getIdRegal() {
        return idRegal;
    }

    public int getxPoczatek() {
        return xPoczatek;
    }


    public int getyPoczatek() {
        return yPoczatek;
    }


    public int getIloscPolek() {
        return iloscPolek;
    }

    public int getWysokosc() {
        return wysokosc;
    }

    public Magazyn getMagazyn() {
        return magazyn;
    }

    public void setIdRegal(int idRegal) {
        this.idRegal = idRegal;
    }

    public void setxPoczatek(int xPoczatek) {
        this.xPoczatek = xPoczatek;
    }


    public void setyPoczatek(int yPoczatek) {
        this.yPoczatek = yPoczatek;
    }


    public void setIloscPolek(int iloscPolek) {
        this.iloscPolek = iloscPolek;
    }

    public void setWysokosc(int wysokosc) {
        this.wysokosc = wysokosc;
    }

    public void setMagazyn(Magazyn magazyn) {
        this.magazyn = magazyn;
    }

    public int getSzerokosc() {
        return szerokosc;
    }

    public void setSzerokosc(int szerokosc) {
        this.szerokosc = szerokosc;
    }

    public int getDlugosc() {
        return dlugosc;
    }

    public void setDlugosc(int dlugosc) {
        this.dlugosc = dlugosc;
    }
}
