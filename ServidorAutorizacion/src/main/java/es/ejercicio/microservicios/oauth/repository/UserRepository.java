package es.ejercicio.microservicios.oauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import es.ejercicio.microservicios.oauth.service.entity.User;


public interface UserRepository extends JpaRepository<User, Long>{

    User findOneByUsername(String username);

}
