package com.tiendabarrio.dao;

import com.tiendabarrio.model.Usuario;
import java.util.List;

public interface UsuarioDAO {

    void crear(Usuario usuario);

    Usuario buscarPorId(int id);

    Usuario buscarPorEmail(String email);

    List<Usuario> listarTodos();

    void actualizar(Usuario usuario);

    void eliminar(int id);
}