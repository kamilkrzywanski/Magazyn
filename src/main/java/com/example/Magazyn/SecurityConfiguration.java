package com.example.Magazyn;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService);
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encodePWD());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .antMatchers("/").permitAll()



                .antMatchers("/magazyny").permitAll()
                .antMatchers("/magazyny/addForm", "/magazyny/dodaj").hasAnyRole("kierownik")


                .antMatchers("/regalyMagazynu/*").permitAll()
                .antMatchers("/regalyMagazynu/addForm/{idMagazyn}").hasAnyRole("kierownik")
                .antMatchers("/regalyMagazynu/dodaj/*").hasAnyRole("kierownik")





                .antMatchers("/produkty").permitAll()
                .antMatchers("/produkty/addForm", "/produkty/dodaj").hasAnyRole("kierownik")
                .antMatchers("/produkty/updateForm").hasAnyRole("kierownik")
                .antMatchers("/produkty/update/{idProdukt}").hasAnyRole("kierownik")
                .antMatchers("/produkty/delete").hasAnyRole("kierownik")



                .antMatchers("/kategorie", "/kategorie/addForm", "/kategorie/dodaj").hasAnyRole("kierownik")
                .antMatchers("/zamowienia/updateForm").hasAnyRole("kierownik")
                .antMatchers("/zamowienia/update/{idZamowienie}").hasAnyRole("kierownik")
                .antMatchers("/kategorie/usun/{idKategoria}").hasAnyRole("kierownik")
                .antMatchers("/kategorie/usun/{idKategoria}").hasAnyRole("kierownik")
                .antMatchers("/kategorie/updateForm").hasAnyRole("kierownik")
                .antMatchers("/kategorie/update/{idKategoria}").hasAnyRole("kierownik")



                .antMatchers("/zamowienia/historia", "/zamowienia", "/zamowienia/addForm", "/zamowienia/dodaj").hasAnyRole("kierownik", "pracownik")
                .antMatchers("/zamowieniaProdukt/*").hasAnyRole("kierownik", "pracownik")
                .antMatchers("/zamowienieProdukty/addForm/{idZamowienie}").hasAnyRole("kierownik")
                .antMatchers("/zamowienieProdukty/dodaj").hasAnyRole("kierownik")
                .antMatchers("/zamowienia/wyznaczTrase/{idZamowienie}").hasAnyRole("kierownik", "pracownik")
                .antMatchers(" /zamowieniaProdukt/usun/{idZamowienieProdukt}").hasAnyRole("kierownik")
                .antMatchers("/zamowienia/zrealizuj/{idZamowienie}").hasAnyRole("kierownik", "pracownik")





                .antMatchers("/regalProdukty/*").hasAnyRole("kierownik", "pracownik")
                .antMatchers("/regalProdukty/addForm/{idRegal}").hasAnyRole("kierownik")
                .antMatchers("/regalProdukty/dodaj").hasAnyRole("kierownik")



                .antMatchers("/dostawy").hasAnyRole("kierownik")
                .antMatchers("/dostawy/dodaj").hasAnyRole("kierownik")
                .antMatchers("/dostawy/dodaj/*").hasAnyRole("kierownik")
                .antMatchers("/dostawy/**").hasAnyRole("kierownik")

                .antMatchers("/kierownicy/dodaj").permitAll()
                .antMatchers("/pracownicy/form/*").hasAnyRole("kierownik")




                .antMatchers("/prevPage").permitAll()
                .antMatchers("/panel").hasRole("administrator")
                .antMatchers("/error").hasRole("administrator")
                .antMatchers("/rejestracja/*").permitAll()
                .anyRequest().authenticated()

               // .anyRequest().hasRole("administrator")
                //.and().formLogin().permitAll()
                .and().
                formLogin().loginPage("/login").defaultSuccessUrl("/").failureUrl("/login?error=true").permitAll()
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?success=true")
                .and()
        ;


    }
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//                .ignoring()
//                .antMatchers("/resources/**");
//    }

    @Bean
    public BCryptPasswordEncoder encodePWD() {
        return new BCryptPasswordEncoder();
    }
}
