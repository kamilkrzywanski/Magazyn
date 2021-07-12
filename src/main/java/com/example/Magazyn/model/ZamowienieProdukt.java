package com.example.Magazyn.model;

import com.example.Magazyn.controller.ZamowienieProduktControler;

import javax.persistence.*;

@Entity
@Table(name = "ZamowienieProdukt")
public class ZamowienieProdukt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zamowienie_produkt")
    private int idZamowienieProdukt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zamowienie", referencedColumnName = "id_zamowienie")
    private Zamowienie zamowienie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produkt", referencedColumnName = "id_produkt")
    private Produkt produkt;

    @Column(name = "ilosc")
    private int ilosc;





    public int getIdZamowienieProdukt() {
        return idZamowienieProdukt;
    }

    public Zamowienie getZamowienie() {
        return zamowienie;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public int getIlosc() {
        return ilosc;
    }


    public ZamowienieProdukt(){

    }

    public ZamowienieProdukt(Zamowienie zamowienie, Produkt produkt, int ilosc) {
        this.zamowienie = zamowienie;
        this.produkt = produkt;
        this.ilosc = ilosc;
    }


    public void setIlosc(int ilosc) {
        this.ilosc = ilosc;
    }
}
