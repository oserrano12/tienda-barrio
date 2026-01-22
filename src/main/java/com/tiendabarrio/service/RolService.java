package com.tiendabarrio.service;

import com.tiendabarrio.model.Rol;
import java.util.List;

public interface RolService {

    void crearRol(Rol rol);

    Rol obtenerRolPorId(int id);

    Rol obtenerRolPorNombre(String nombre);

    List<Rol> listarRoles();

    void actualizarRol(Rol rol);

    void eliminarRol(int id);
}