package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.VentaDAO;
import com.tiendabarrio.model.Venta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VentaDAOImpl implements VentaDAO {

    @Override
    public void crear(Venta venta) {
        String sql = "INSERT INTO venta (fecha_venta, total_venta, usuario_id) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setTimestamp(1, Timestamp.valueOf(venta.getFechaVenta()));
            ps.setBigDecimal(2, venta.getTotalVenta());
            ps.setInt(3, venta.getUsuarioId());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venta.setVentaId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear venta", e);
        }
    }

    @Override
    public void crear(Venta venta, Connection conn) {
        String sql = "INSERT INTO venta (fecha_venta, total_venta, usuario_id) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setTimestamp(1, Timestamp.valueOf(venta.getFechaVenta()));
            ps.setBigDecimal(2, venta.getTotalVenta());
            ps.setInt(3, venta.getUsuarioId());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    venta.setVentaId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear venta", e);
        }
    }

    @Override
    public Venta buscarPorId(int ventaId) {
        String sql = "SELECT * FROM venta WHERE venta_id = ?";
        Venta venta = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ventaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    venta = mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar venta por ID", e);
        }

        return venta;
    }

    @Override
    public List<Venta> listarTodos() {
        String sql = "SELECT * FROM venta";
        List<Venta> ventas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ventas.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar todas las ventas", e);
        }

        return ventas;
    }

    @Override
    public List<Venta> listarPorUsuario(int usuarioId) {
        String sql = "SELECT * FROM venta WHERE usuario_id = ?";
        List<Venta> ventas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, usuarioId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ventas.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar ventas por usuario", e);
        }

        return ventas;
    }

    @Override
    public void eliminar(int ventaId) {
        String sql = "DELETE FROM venta WHERE venta_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ventaId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar venta", e);
        }
    }

    private Venta mapear(ResultSet rs) throws SQLException {
        Venta v = new Venta();
        v.setVentaId(rs.getInt("venta_id"));
        v.setFechaVenta(rs.getTimestamp("fecha_venta").toLocalDateTime());
        v.setTotalVenta(rs.getBigDecimal("total_venta"));
        v.setUsuarioId(rs.getInt("usuario_id"));
        return v;
    }
}