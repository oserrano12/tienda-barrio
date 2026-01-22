package com.tiendabarrio.service;

import com.tiendabarrio.model.Usuario;
import java.util.List;

public interface UsuarioService {

    void crearUsuario(Usuario usuario);

    Usuario obtenerUsuarioPorId(int id);

    Usuario obtenerUsuarioPorEmail(String email);

    List<Usuario> listarUsuarios();

    void actualizarUsuario(Usuario usuario);

    void cambiarEstadoUsuario(int id, boolean activo);
}
