package es.ejercicio.microservicios.zuul.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(value = -1)
public class SecurityConfiguration
    extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/uaa/**", "/biblioteca/**").permitAll().and()
            .authorizeRequests().anyRequest().authenticated();
        http.csrf().disable();
        http.httpBasic().disable();

    }
}
