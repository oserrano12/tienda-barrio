package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.ProveedorDAO;
import com.tiendabarrio.model.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAOImpl implements ProveedorDAO {

    @Override
    public void crear(Proveedor proveedor) {
        String sql = "INSERT INTO proveedor (nombre_proveedor, telefono_proveedor, email_proveedor, direccion_proveedor) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, proveedor.getNombreProveedor());
            ps.setString(2, proveedor.getTelefonoProveedor());
            ps.setString(3, proveedor.getEmailProveedor());
            ps.setString(4, proveedor.getDireccionProveedor());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear proveedor", e);
        }
    }

    @Override
    public Proveedor buscarPorId(int id) {
        String sql = "SELECT * FROM proveedor WHERE proveedor_id = ?";
        Proveedor proveedor = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    proveedor = mapearProveedor(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar proveedor por id", e);
        }

        return proveedor;
    }

    @Override
    public Proveedor buscarPorEmail(String email) {
        String sql = "SELECT * FROM proveedor WHERE email_proveedor = ?";
        Proveedor proveedor = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    proveedor = mapearProveedor(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar proveedor por email", e);
        }

        return proveedor;
    }

    @Override
    public List<Proveedor> listarTodos() {
        String sql = "SELECT * FROM proveedor";
        List<Proveedor> proveedores = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                proveedores.add(mapearProveedor(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar proveedores", e);
        }

        return proveedores;
    }

    @Override
    public void actualizar(Proveedor proveedor) {
        String sql = "UPDATE proveedor SET nombre_proveedor = ?, telefono_proveedor = ?, email_proveedor = ?, direccion_proveedor = ? WHERE proveedor_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, proveedor.getNombreProveedor());
            ps.setString(2, proveedor.getTelefonoProveedor());
            ps.setString(3, proveedor.getEmailProveedor());
            ps.setString(4, proveedor.getDireccionProveedor());
            ps.setInt(5, proveedor.getProveedorId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar proveedor", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM proveedor WHERE proveedor_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar proveedor", e);
        }
    }

    private Proveedor mapearProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setProveedorId(rs.getInt("proveedor_id"));
        proveedor.setNombreProveedor(rs.getString("nombre_proveedor"));
        proveedor.setTelefonoProveedor(rs.getString("telefono_proveedor"));
        proveedor.setEmailProveedor(rs.getString("email_proveedor"));
        proveedor.setDireccionProveedor(rs.getString("direccion_proveedor"));
        return proveedor;
    }
}