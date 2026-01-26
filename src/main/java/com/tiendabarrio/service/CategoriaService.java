package com.tiendabarrio.service;

import com.tiendabarrio.model.Categoria;
import java.util.List;

public interface CategoriaService {
    void crearCategoria(Categoria categoria);
    Categoria buscarCategoriaPorId(int id);
    List<Categoria> listarTodasCategorias();
    void actualizarCategoria(Categoria categoria);
    void eliminarCategoria(int id);
    boolean existeCategoriaConNombre(String nombre);
}