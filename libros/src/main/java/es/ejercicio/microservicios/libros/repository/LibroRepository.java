package es.ejercicio.microservicios.libros.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import es.ejercicio.microservicios.libros.entity.Libro;


//https://github.com/spring-projects/spring-data-examples/tree/master/jpa/query-by-example
@Repository
public interface LibroRepository extends JpaRepository<Libro, Integer>, QueryByExampleExecutor<Libro> {

    public List<Libro> findByFavoriteTrue();
}