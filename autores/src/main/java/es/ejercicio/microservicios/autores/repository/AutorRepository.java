package es.ejercicio.microservicios.autores.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ejercicio.microservicios.autores.entity.Autor;



@Repository
public interface AutorRepository extends JpaRepository<Autor, Integer> {


}