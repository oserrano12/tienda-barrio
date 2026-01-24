package com.tiendabarrio.dao;

import java.util.List;

public interface UsuarioRolDAO {

    void asignarRol(int usuarioId, int rolId);

    void eliminarRol(int usuarioId, int rolId);

    void eliminarRolesPorUsuario(int usuarioId);

    List<Integer> obtenerRolesPorUsuario(int usuarioId);
}