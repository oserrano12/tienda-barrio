package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.UsuarioRolDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRolDAOImpl implements UsuarioRolDAO {

    @Override
    public void asignarRol(int usuarioId, int rolId) {
        // CAMBIO: rol_id -> id_rol
        String sql = "INSERT INTO usuario_rol (usuario_id, id_rol) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, rolId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al asignar rol al usuario", e);
        }
    }

    @Override
    public void eliminarRol(int usuarioId, int rolId) {
        // CAMBIO: rol_id -> id_rol
        String sql = "DELETE FROM usuario_rol WHERE usuario_id = ? AND id_rol = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.setInt(2, rolId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar rol del usuario", e);
        }
    }

    @Override
    public void eliminarRolesPorUsuario(int usuarioId) {
        String sql = "DELETE FROM usuario_rol WHERE usuario_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar roles del usuario", e);
        }
    }

    @Override
    public List<Integer> obtenerRolesPorUsuario(int usuarioId) {
        // CAMBIO: rol_id -> id_rol
        String sql = "SELECT id_rol FROM usuario_rol WHERE usuario_id = ?";
        List<Integer> roles = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    roles.add(rs.getInt("id_rol"));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener roles del usuario", e);
        }

        return roles;
    }
}