package com.tiendabarrio.service.impl;

import com.tiendabarrio.dao.UsuarioDAO;
import com.tiendabarrio.dao.impl.UsuarioDAOImpl;
import com.tiendabarrio.model.Usuario;
import com.tiendabarrio.service.UsuarioService;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;

    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser null");
        }
        usuarioDAO.crear(usuario);
    }

    @Override
    public Usuario obtenerUsuarioPorId(int id) {
        return usuarioDAO.buscarPorId(id);
    }

    @Override
    public Usuario obtenerUsuarioPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    @Override
    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarTodos();
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getUsuarioId() <= 0) {
            throw new IllegalArgumentException("Usuario inválido");
        }
        usuarioDAO.actualizar(usuario);
    }

    @Override
    public void cambiarEstadoUsuario(int id, boolean activo) {
        usuarioDAO.cambiarEstado(id, activo);
    }
}