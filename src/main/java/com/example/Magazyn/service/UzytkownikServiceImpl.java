package com.example.Magazyn.service;

import java.util.List;

import com.example.Magazyn.model.Uzytkownik;
import com.example.Magazyn.repository.UzytkownikRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UzytkownikServiceImpl implements UzytkownikService
{
	@Autowired
    public UzytkownikRepository uzytkownikRepository;

    @Override
	public Uzytkownik createUzytkownik(Uzytkownik uzytkownik) { return uzytkownikRepository.save(uzytkownik); }

    @Override
    public List<Uzytkownik> getAllUzytkownik() { return this.uzytkownikRepository.findAll();  }

    @Override
    public void removeUzytkownik(Integer id) { this.uzytkownikRepository.deleteById(id); }

	@Override
	public Uzytkownik getOneById(Integer id) {return this.uzytkownikRepository.getOne(id);}

    @Override
    public Uzytkownik getLoggedUserDetails(Authentication authentication) {
        return uzytkownikRepository.findByLogin(authentication.getName());
    }
}
