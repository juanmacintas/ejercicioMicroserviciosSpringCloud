package es.ejercicio.microservicios.biblioteca.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class BibliotecaConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
             authorizeRequests().
             antMatchers("/biblioteca/**").permitAll();
    }

}