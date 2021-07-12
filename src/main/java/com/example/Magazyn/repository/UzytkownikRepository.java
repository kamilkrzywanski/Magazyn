package com.example.Magazyn.repository;
import com.example.Magazyn.model.Uzytkownik;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UzytkownikRepository  extends JpaRepository<Uzytkownik,Integer>
{
	Uzytkownik findByLogin(String userLoginName);
}

