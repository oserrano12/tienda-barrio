package com.tiendabarrio.dao;

import com.tiendabarrio.model.Usuario;
import java.util.List;

// Define las operaciones de acceso a datos para Usuario
public interface UsuarioDAO {

    void crear(Usuario usuario);

    Usuario buscarPorId(int id);

    Usuario buscarPorEmail(String email);

    List<Usuario> listarTodos();

    void actualizar(Usuario usuario);

    void cambiarEstado(int id, boolean activo);
}
