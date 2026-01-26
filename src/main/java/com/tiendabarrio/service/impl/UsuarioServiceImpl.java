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
            throw new RuntimeException("Email ya registrado");
        }

        usuarioDAO.crear(usuario);

        for (Integer rolId : rolesIds) {
            usuarioRolDAO.asignarRol(usuario.getUsuarioId(), rolId);
        }
    }

    @Override
    public Usuario buscarPorId(int usuarioId) {
        if (usuarioId <= 0) throw new IllegalArgumentException("ID inválido");
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");
        return usuario;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email vacío");
        return usuarioDAO.buscarPorEmail(email.trim());
    }

    @Override
    public List<Usuario> listarTodos() {
        return usuarioDAO.listarTodos();
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario.getUsuarioId() <= 0) throw new IllegalArgumentException("ID inválido");
        if (usuario.getNombreUsuario() == null || usuario.getNombreUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }
        if (usuario.getEmailUsuario() == null || usuario.getEmailUsuario().trim().isEmpty()) {
            throw new IllegalArgumentException("Email obligatorio");
        }

        Usuario existente = usuarioDAO.buscarPorId(usuario.getUsuarioId());
        if (existente == null) throw new RuntimeException("Usuario no encontrado");

        usuarioDAO.actualizar(usuario);
    }

    @Override
    public void desactivarUsuario(int usuarioId) {
        if (usuarioId <= 0) throw new IllegalArgumentException("ID inválido");
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");

        usuario.setActivo(false);
        usuarioDAO.actualizar(usuario);
    }

    @Override
    public void activarUsuario(int usuarioId) {
        if (usuarioId <= 0) throw new IllegalArgumentException("ID inválido");
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");

        usuario.setActivo(true);
        usuarioDAO.actualizar(usuario);
    }

    @Override
    public void cambiarPassword(int usuarioId, String nuevaPassword) {
        if (usuarioId <= 0) throw new IllegalArgumentException("ID inválido");
        if (nuevaPassword == null || nuevaPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password vacío");
        }

        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);
        if (usuario == null) throw new RuntimeException("Usuario no encontrado");

        usuario.setPasswordUsuario(nuevaPassword.trim());
        usuarioDAO.actualizar(usuario);
    }

    @Override
    public Usuario login(String email, String password) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email vacío");
        if (password == null || password.trim().isEmpty()) throw new IllegalArgumentException("Password vacío");

        Usuario usuario = usuarioDAO.buscarPorEmail(email.trim());
        if (usuario == null) throw new RuntimeException("Credenciales inválidas");
        if (!usuario.isActivo()) throw new RuntimeException("Usuario inactivo");
        if (!usuario.getPasswordUsuario().equals(password.trim())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return usuario;
    }
}