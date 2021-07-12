package com.example.Magazyn.service;


import com.example.Magazyn.repository.UzytkownikRepository;
import com.example.Magazyn.model.MyUserDetails;
import com.example.Magazyn.model.Uzytkownik;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UzytkownikRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Uzytkownik user = userRepository.findByLogin(userName);

        MyUserDetails userDetails = null;
        if (user != null) {
            userDetails = new MyUserDetails(user);
        } else {
            throw new UsernameNotFoundException("User not exist with name : " + userName);
        }
        return userDetails;
    }

}
