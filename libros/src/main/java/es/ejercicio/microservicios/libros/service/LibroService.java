package es.ejercicio.microservicios.libros.service;

import java.util.List;

import es.ejercicio.microservicios.libros.entity.Libro;

public interface LibroService {

    public List<Libro> findByFavoriteTrue();

    public List<Libro> findAll();

    public List<Libro> findByExample(Libro libro);
}