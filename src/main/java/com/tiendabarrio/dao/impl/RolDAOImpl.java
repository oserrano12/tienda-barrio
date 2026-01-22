package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.RolDAO;
import com.tiendabarrio.model.Rol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Implementación JDBC del DAO Rol
public class RolDAOImpl implements RolDAO {

    @Override
    public void crear(Rol rol) {
        String sql = "INSERT INTO rol (nombre_rol, descripcion_rol) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rol.getNombreRol());
            ps.setString(2, rol.getDescripcionRol());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear rol", e);
        }
    }

    @Override
    public Rol buscarPorId(int id) {
        String sql = "SELECT * FROM rol WHERE id_rol = ?";
        Rol rol = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = mapearRol(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar rol por id", e);
        }

        return rol;
    }

    @Override
    public Rol buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM rol WHERE nombre_rol = ?";
        Rol rol = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rol = mapearRol(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar rol por nombre", e);
        }

        return rol;
    }

    @Override
    public List<Rol> listarTodos() {
        String sql = "SELECT * FROM rol";
        List<Rol> roles = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                roles.add(mapearRol(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar roles", e);
        }

        return roles;
    }

    @Override
    public void actualizar(Rol rol) {
        String sql = "UPDATE rol SET nombre_rol = ?, descripcion_rol = ? WHERE id_rol = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rol.getNombreRol());
            ps.setString(2, rol.getDescripcionRol());
            ps.setInt(3, rol.getIdRol());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar rol", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM rol WHERE id_rol = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar rol", e);
        }
    }

    // Convierte un ResultSet en un objeto Rol
    private Rol mapearRol(ResultSet rs) throws SQLException {
        Rol rol = new Rol();
        rol.setIdRol(rs.getInt("id_rol"));
        rol.setNombreRol(rs.getString("nombre_rol"));
        rol.setDescripcionRol(rs.getString("descripcion_rol"));
        return rol;
    }
}