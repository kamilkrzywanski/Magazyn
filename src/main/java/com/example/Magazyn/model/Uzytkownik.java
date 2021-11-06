package com.example.Magazyn.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.persistence.*;

@Entity
@Table(name="Uzytkownik")
public class Uzytkownik 
{
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_uzytkownik")
	private int idUzytkownik;
	
	@Column(name="login", nullable=false, length=50, unique = true) // w @Column dane odpowiadające tym w bazie
	private String login;
	
	@Column(name="haslo", nullable=false, length=255)
	private String haslo;
	
	@Column(name = "email", nullable=false, length=50, unique = true)
	private String email;



	@ManyToOne
	@JoinColumn(name="id_rola", nullable=false)
	private Rola rola;

	@OneToOne(mappedBy = "Uzytkownik", cascade = CascadeType.ALL) // kaskadowe operacje ustawiamy zawsze na rodzicu
																  // (czyli tabela do której odwołuje się inna przez klucz obcy.
												// W tym wypadku Pracownik odwołuje się do uzytkownika)
	private Pracownik pracownik;


	//


	public Uzytkownik() {}  // hibernate wymaga konstruktora bezparametrowego

	public Uzytkownik(String login, String haslo, String email, Rola rola)
	{
		this.login = login;
		this.haslo = haslo;
		this.email = email;
		this.rola = rola;
	}

	public Uzytkownik(int idUzytkownik, String login, String haslo, String email, Pracownik pracownik, Rola rola)
	{
		this.idUzytkownik = idUzytkownik;
		this.login = login;
		this.haslo = haslo;
		this.email = email;
		this.rola = rola;
		this.pracownik = pracownik;
	}

	public Uzytkownik(int idUzytkownik, String login, String haslo, String email, Rola rola)
	{
		this.idUzytkownik = idUzytkownik;
		this.login = login;
		this.haslo = haslo;
		this.email = email;
		this.rola = rola;

	}

	// settery, gettery, kontruktory, toStringi mając zmienne można stworzyć szybko przez alt + ins w intellij

	public int getIdUzytkownik() 
	{
		return idUzytkownik;
	}

	public void setIdUzytkownik(int idUzytkownik) 
	{
		this.idUzytkownik = idUzytkownik;
	}

	public String getLogin() 
	{
		return login;
	}

	public void setLogin(String login) 
	{
		this.login = login;
	}

	public String getHaslo() {
		return haslo;
	}

	public void setHaslo(String haslo) 
	{
		this.haslo = haslo;
	}

	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}
	




	public Rola getRola() {
		return rola;
	}

	public void setRola(Rola rola) {
		this.rola = rola;
	}

	public Pracownik getPracownik() {
		return pracownik;
	}

	public void setPracownik(Pracownik pracownik) {
		this.pracownik = pracownik;
	}


	@Override
	public String toString() {
		return "Uzytkownik{" +
				"idUzytkownik=" + idUzytkownik +
				", login='" + login + '\'' +
				", haslo='" + haslo + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}
