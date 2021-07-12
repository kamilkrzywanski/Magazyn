package com.example.Magazyn.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Pracownik")
public class Pracownik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pracownik")
    private int idPracownik;

    @Column(name = "imie", nullable=false, length=50)
    private String imie;

    @Column(name = "nazwisko", nullable=false, length=50)
    private String nazwisko;

    @Column(name = "telefon", nullable=false, length=9)
    private String telefon;


    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY) // na dziecku robimy PRESIST żeby zaś problemu z updejtami nie było
    @JoinColumn(name = "id_uzytkownik", referencedColumnName = "id_uzytkownik")
    private Uzytkownik Uzytkownik;

    @OneToMany(cascade = CascadeType.PERSIST) // na dziecku robimy PRESIST żeby zaś problemu z updejtami nie było
    @JoinColumn(name = "id_pracownik", referencedColumnName = "id_pracownik")
    private List<Zamowienie> zamowienie = new ArrayList<Zamowienie>();

    public Pracownik() {}

    public Pracownik(String imie, String nazwisko, String telefon) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
    }

    public Pracownik(String imie, String nazwisko, String telefon, Uzytkownik uzytkownik) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.telefon = telefon;
        this.Uzytkownik = uzytkownik;
    }

    public int getIdPracownik() {
        return idPracownik;
    }

    public void setIdPracownik(int idPracownik) {
        this.idPracownik = idPracownik;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Uzytkownik getUzytkownik() {
        return Uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        Uzytkownik = uzytkownik;
    }





    @Override
    public String toString() {
        return "Pracownik{" +
                "idPracownik=" + idPracownik +
                ", imie='" + imie + '\'' +
                ", nazwisko='" + nazwisko + '\'' +
                ", telefon='" + telefon + '\'' +
                '}';
    }
}
