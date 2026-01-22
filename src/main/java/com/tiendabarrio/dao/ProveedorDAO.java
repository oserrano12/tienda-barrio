package com.tiendabarrio.dao;

import com.tiendabarrio.model.Proveedor;
import java.util.List;

public interface ProveedorDAO {

    void crear(Proveedor proveedor);

    Proveedor buscarPorId(int id);

    Proveedor buscarPorEmail(String email);

    List<Proveedor> listarTodos();

    void actualizar(Proveedor proveedor);

    void eliminar(int id);
}