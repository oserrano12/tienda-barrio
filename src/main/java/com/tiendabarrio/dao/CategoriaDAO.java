package com.tiendabarrio.dao;

import com.tiendabarrio.model.Categoria;
import java.util.List;

public interface CategoriaDAO {

    void crear(Categoria categoria);

    Categoria buscarPorId(int id);

    List<Categoria> listarTodos();

    void actualizar(Categoria categoria);

    void eliminar(int id);
}