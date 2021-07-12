package com.example.Magazyn.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.Magazyn.model.Uzytkownik;
import com.example.Magazyn.service.UzytkownikServiceImpl;


@Controller
public class UzytkownikController 
{
	 @Autowired
	    private UzytkownikServiceImpl uzytkownikServiceImpl;
	  
	    private ModelAndView modelAndView;

	    @GetMapping("/uzytkownicy")
	    public ModelAndView getAllProduct() {

	        modelAndView = new ModelAndView("uzytkownik/uzytkownik");
	        modelAndView.addObject("uzytkownicy", uzytkownikServiceImpl.getAllUzytkownik());
	        return modelAndView;
	    }

	    @PostMapping("/uzytkownik")
	    public ResponseEntity <Uzytkownik> createProduct(@RequestBody Uzytkownik uzytkownik) {

	        return ResponseEntity.ok().body(this.uzytkownikServiceImpl.createUzytkownik(uzytkownik));
	    }
	    
	    @GetMapping("/uzytkownik/addForm")
	    public ModelAndView addformUzytkownik() {

	        modelAndView = new ModelAndView("turysta/addForm");
	        return modelAndView;
	    }
	    
}
