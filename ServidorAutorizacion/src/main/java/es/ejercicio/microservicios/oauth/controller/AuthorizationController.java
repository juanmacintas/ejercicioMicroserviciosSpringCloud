package es.ejercicio.microservicios.oauth.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthorizationController {

    @RequestMapping("/user")
    public Principal user(Principal user) {

        log.info("Start {} -> user :: {}", this.getClass().getSimpleName(),
            String.valueOf(user != null));

        return user;
    }
}
