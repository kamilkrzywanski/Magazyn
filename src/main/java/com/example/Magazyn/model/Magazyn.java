package com.example.Magazyn.model;

import org.springframework.web.bind.annotation.MatrixVariable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Magazyn")
public class Magazyn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_magazyn")
    private int idMagazyn;

    @Column(name = "nazwa", nullable=true, length=50)
    private String nazwa;

    @Column(name = "lokalizacja", nullable=true, length=100)
    private String lokalizacja;

    @Column(name = "dlugosc")
    private int dlugosc;

    @Column(name = "szerokosc")
    private int szerokosc;

    @Column(name = "wysokosc")
    private int wysokosc;

    @Column(name = "odstepy")
    private int odstepy;

    @Column(name = "x_wejscie")
    private int xWejscie;

    @Column(name = "y_wejscie")
    private int yWejscie;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_magazyn", referencedColumnName = "id_magazyn")
    private List<Regal> regal = new ArrayList<Regal>();


    @ManyToMany(cascade = CascadeType.PERSIST) // na dziecku robimy PRESIST żeby zaś problemu z updejtami nie było
    @JoinColumn(name = "id_magazyn", referencedColumnName = "id_magazyn")
    private List<Zamowienie> zamowienie = new ArrayList<Zamowienie>();

    public Magazyn() {
    }


    public Magazyn(String nazwa, String lokalizacja, int dlugosc, int szerokosc, int wysokosc, int odstepy, int xWejscie, int yWejscie) {
        this.nazwa = nazwa;
        this.lokalizacja = lokalizacja;
        this.dlugosc = dlugosc;
        this.szerokosc = szerokosc;
        this.wysokosc = wysokosc;
        this.odstepy = odstepy;
        this.xWejscie = xWejscie;
        this.yWejscie = yWejscie;
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getLokalizacja() {
        return lokalizacja;
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

    public int getIdMagazyn() {
        return idMagazyn;
    }

    public int getOdstepy() {
        return odstepy;
    }

    public int getxWejscie() {
        return xWejscie;
    }

    public int getyWejscie() {
        return yWejscie;
    }
}
