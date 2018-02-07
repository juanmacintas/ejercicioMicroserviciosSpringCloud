package es.ejercicio.microservicios.editoriales.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ejercicio.microservicios.editoriales.entity.Editorial;



@Repository
public interface EditorialRepository extends JpaRepository<Editorial, Integer> {


}