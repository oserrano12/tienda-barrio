package com.tiendabarrio.service;

import com.tiendabarrio.model.Usuario;

import java.util.List;

public interface UsuarioService {

    void registrarUsuario(Usuario usuario, List<Integer> rolesIds);

    Usuario buscarPorId(int usuarioId);

    Usuario buscarPorEmail(String email);

    List<Usuario> listarTodos();

    void desactivarUsuario(int usuarioId);

    Usuario login(String email, String password);
}