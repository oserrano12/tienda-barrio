package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.UsuarioDAO;
import com.tiendabarrio.model.Usuario;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Implementación JDBC del DAO Usuario
public class UsuarioDAOImpl implements UsuarioDAO {

    @Override
    public void crear(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre_usuario, email_usuario, password_usuario, activo) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getEmailUsuario());
            ps.setString(3, usuario.getPasswordUsuario());
            ps.setBoolean(4, usuario.isActivo());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario", e);
        }
    }

    @Override
    public Usuario buscarPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE usuario_id = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por id", e);
        }

        return usuario;
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email_usuario = ?";
        Usuario usuario = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    usuario = mapearUsuario(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario por email", e);
        }

        return usuario;
    }

    @Override
    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(mapearUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar usuarios", e);
        }

        return usuarios;
    }

    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre_usuario = ?, email_usuario = ?, password_usuario = ?, activo = ? " +
                "WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombreUsuario());
            ps.setString(2, usuario.getEmailUsuario());
            ps.setString(3, usuario.getPasswordUsuario());
            ps.setBoolean(4, usuario.isActivo());
            ps.setInt(5, usuario.getUsuarioId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        String sql = "UPDATE usuario SET activo = ? WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, id);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado del usuario", e);
        }
    }

    // Convierte un ResultSet en un objeto Usuario
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setUsuarioId(rs.getInt("usuario_id"));
        usuario.setNombreUsuario(rs.getString("nombre_usuario"));
        usuario.setEmailUsuario(rs.getString("email_usuario"));
        usuario.setPasswordUsuario(rs.getString("password_usuario"));
        usuario.setActivo(rs.getBoolean("activo"));

        Timestamp ts = rs.getTimestamp("fecha_creacion");
        if (ts != null) {
            usuario.setFechaCreacion(ts.toLocalDateTime());
        }

        return usuario;
    }
}