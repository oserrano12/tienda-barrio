package com.tiendabarrio.service;

import com.tiendabarrio.model.Usuario;
import java.util.List;

public interface UsuarioService {
    void registrarUsuario(Usuario usuario, List<Integer> rolesIds);
    Usuario buscarPorId(int usuarioId);
    Usuario buscarPorEmail(String email);
    List<Usuario> listarTodos();
    void actualizarUsuario(Usuario usuario);
    void desactivarUsuario(int usuarioId);
    void activarUsuario(int usuarioId);
    void cambiarPassword(int usuarioId, String nuevaPassword);
    Usuario login(String email, String password);
}