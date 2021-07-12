package com.example.Magazyn.model;


import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Zamowienie")
public class Zamowienie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zamowienie")
    private int idZamowienie;

    @Column(name = "adres", nullable=true, length=150)
    private String adres;

    @Column(name = "status", nullable=true, length=150)
    private int status;


    @ManyToOne
    @JoinColumn(name = "id_pracownik", referencedColumnName = "id_pracownik")
    private Pracownik pracownik;

    @ManyToOne
    @JoinColumn(name = "id_magazyn", referencedColumnName = "id_magazyn")
    private Magazyn magazyn;


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.DATE)
    @Column(name = "data_zlozenia")
    private Date dataZlozenia;



    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(value = TemporalType.DATE)
    @Column(name = "termin_realizacji")
    private Date terminRealizacji;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_zamowienie", referencedColumnName = "id_zamowienie")
    private List<ZamowienieProdukt> zamowienieProdukt = new ArrayList<ZamowienieProdukt>();

    public Zamowienie() {

    }

    public Zamowienie(String adres, Pracownik pracownik) {
        this.adres = adres;
        this.pracownik = pracownik;
    }

    public Zamowienie(String adres, Pracownik pracownik, Date dataZlozenia, Date terminRealizacji, Magazyn magazyn) {
        this.adres = adres;
        this.pracownik = pracownik;
        this.dataZlozenia = dataZlozenia;
        this.terminRealizacji = terminRealizacji;
        this.magazyn = magazyn;
        this.status = 0;
    }

    public int getIdZamowienie() {
        return idZamowienie;
    }

    public String getAdres() {
        return adres;
    }

    public Date getDataZlozenia() {
        return dataZlozenia;
    }

    public Date getTerminRealizacji() {
        return terminRealizacji;
    }

    public List<ZamowienieProdukt> getZamowienieProdukt() {
        return zamowienieProdukt;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }


    public void setIdZamowienie(int idZamowienie) {
        this.idZamowienie = idZamowienie;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public void setZamowienieProdukt(List<ZamowienieProdukt> zamowienieProdukt) {
        this.zamowienieProdukt = zamowienieProdukt;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }

    public void setDataZlozenia(Date dataZlozenia) {
        this.dataZlozenia = dataZlozenia;
    }

    public void setTerminRealizacji(Date terminRealizacji) {
        this.terminRealizacji = terminRealizacji;
    }

    public void setMagazyn(Magazyn magazyn) {
        this.magazyn = magazyn;
    }

    public Magazyn getMagazyn() {
        return magazyn;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
