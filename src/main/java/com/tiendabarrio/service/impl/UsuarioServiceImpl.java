package com.tiendabarrio.service.impl;

import com.tiendabarrio.dao.UsuarioDAO;
import com.tiendabarrio.dao.UsuarioRolDAO;
import com.tiendabarrio.dao.impl.UsuarioDAOImpl;
import com.tiendabarrio.dao.impl.UsuarioRolDAOImpl;
import com.tiendabarrio.model.Usuario;
import com.tiendabarrio.service.UsuarioService;

import java.util.List;

public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioDAO usuarioDAO;
    private final UsuarioRolDAO usuarioRolDAO;

    public UsuarioServiceImpl() {
        this.usuarioDAO = new UsuarioDAOImpl();
        this.usuarioRolDAO = new UsuarioRolDAOImpl();
    }

    @Override
    public void registrarUsuario(Usuario usuario, List<Integer> rolesIds) {
        if (usuarioDAO.buscarPorEmail(usuario.getEmailUsuario()) != null) {
            throw new RuntimeException("El email ya está registrado");
        }

        usuarioDAO.crear(usuario);

        for (Integer rolId : rolesIds) {
            usuarioRolDAO.asignarRol(usuario.getUsuarioId(), rolId);
        }
    }

    @Override
    public Usuario buscarPorId(int usuarioId) {
        return usuarioDAO.buscarPorId(usuarioId);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        return usuarioDAO.buscarPorEmail(email);
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    @Override
    public void desactivarUsuario(int usuarioId) {
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);

        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        usuario.setActivo(false);
        usuarioDAO.actualizar(usuario);
    }

    @Override
    public Usuario login(String email, String password) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        if (usuario == null) {
            throw new RuntimeException("Credenciales inválidas");
        }

        if (!usuario.isActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }

        if (!usuario.getPasswordUsuario().equals(password)) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return usuario;
    }
}