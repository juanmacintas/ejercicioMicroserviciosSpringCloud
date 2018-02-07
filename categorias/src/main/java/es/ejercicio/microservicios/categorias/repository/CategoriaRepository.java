package es.ejercicio.microservicios.categorias.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ejercicio.microservicios.categorias.entity.Categoria;



@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {


}