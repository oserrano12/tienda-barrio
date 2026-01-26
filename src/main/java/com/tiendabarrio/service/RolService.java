package com.tiendabarrio.service;

import com.tiendabarrio.model.Rol;
import java.util.List;

public interface RolService {
    void crearRol(Rol rol);
    Rol buscarRolPorId(int id);
    Rol buscarRolPorNombre(String nombre);
    List<Rol> listarTodosRoles();
    void actualizarRol(Rol rol);
    void eliminarRol(int id);
}