package com.tiendabarrio.dao;

import com.tiendabarrio.model.Producto;
import java.util.List;

public interface ProductoDAO {

    void crear(Producto producto);

    Producto buscarPorId(int id);

    List<Producto> listarTodos();

    List<Producto> listarActivos();

    void actualizar(Producto producto);

    void cambiarEstado(int id, boolean activo);
}