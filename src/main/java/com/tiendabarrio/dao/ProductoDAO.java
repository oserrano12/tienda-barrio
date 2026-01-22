package com.tiendabarrio.dao;

import com.tiendabarrio.model.Producto;
import java.util.List;

public interface ProductoDAO {

    void crear(Producto producto);

    Producto buscarPorId(int productoId);

    List<Producto> listarTodos();

    void actualizar(Producto producto);

    void eliminar(int productoId);
}