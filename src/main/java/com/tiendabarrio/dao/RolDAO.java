package com.tiendabarrio.dao;

import com.tiendabarrio.model.Rol;
import java.util.List;

public interface RolDAO {

    void crear(Rol rol);

    Rol buscarPorId(int id);

    Rol buscarPorNombre(String nombre);

    List<Rol> listarTodos();

    void actualizar(Rol rol);

    void eliminar(int id);
}