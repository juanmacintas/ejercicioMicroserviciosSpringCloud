package es.ejercicio.microservicios.categorias.service;

import java.util.List;

import es.ejercicio.microservicios.categorias.entity.Categoria;

public interface CategoriaService {

    public List<Categoria> findAll();

    public Categoria findById(Integer id);

    public void deleteById(Integer id);

    public Categoria nuevaCategoria(Categoria categoria);
}