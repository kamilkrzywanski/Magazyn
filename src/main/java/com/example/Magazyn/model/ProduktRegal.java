package com.example.Magazyn.model;

import javax.persistence.*;

@Entity
@Table(name = "ProduktRegal")
public class ProduktRegal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_produkt_regal")
    private int idProduktRegal;

    @ManyToOne
    @JoinColumn(name = "id_produkt", referencedColumnName = "id_produkt")
    private Produkt produkt;

    @ManyToOne
    @JoinColumn(name = "id_regal", referencedColumnName = "id_regal")
    private Regal regal;

    @Column(name = "polka")
    private int polka;

    @Column(name = "nr_kolejnosc")
    private int nrKolejnosc;

    @Column(name = "x_produktu")
    private int xProduktu;

    @Column(name = "y_produktu")
    private int yProduktu;





    public ProduktRegal() {
    }


    public ProduktRegal( Produkt produkt, Regal regal, int polka, int nrKolejnosc) {
        this.produkt = produkt;
        this.regal = regal;
        this.polka = polka;
        this.nrKolejnosc = nrKolejnosc;


        if(regal.getSzerokosc()>regal.getDlugosc())
        {
            this.xProduktu=regal.getxPoczatek()+(nrKolejnosc-nrKolejnosc%10)/10000;
            this.yProduktu=regal.getyPoczatek();
        }
        else
        {
            this.xProduktu=regal.getxPoczatek();
            this.yProduktu=regal.getyPoczatek()+(nrKolejnosc-nrKolejnosc%10)/10000;
        }
    }

    public int getIdProduktRegal() {
        return idProduktRegal;
    }

    public Produkt getProdukt() {
        return produkt;
    }

    public Regal getRegal() {
        return regal;
    }

    public int getPolka() {
        return polka;
    }

    public int getNrKolejnosc() {
        return nrKolejnosc;
    }

    public int getxProduktu() {
        return xProduktu;
    }

    public int getyProduktu() {
        return yProduktu;
    }

    public int getIdProdukt() {
        return produkt.getIdProdukt();
    }



}
