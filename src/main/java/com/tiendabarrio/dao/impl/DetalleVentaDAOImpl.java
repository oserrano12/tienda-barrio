package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.DetalleVentaDAO;
import com.tiendabarrio.model.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleVentaDAOImpl implements DetalleVentaDAO {

    @Override
    public void crear(DetalleVenta detalleVenta) {
        String sql = "INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, detalleVenta.getVentaId());
            ps.setInt(2, detalleVenta.getProductoId());
            ps.setInt(3, detalleVenta.getCantidad());
            ps.setBigDecimal(4, detalleVenta.getPrecioUnitario());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalleVenta.setDetalleId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear detalle de venta", e);
        }
    }

    @Override
    public void crear(DetalleVenta detalleVenta, Connection conn) {
        String sql = "INSERT INTO detalle_venta (venta_id, producto_id, cantidad, precio_unitario) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, detalleVenta.getVentaId());
            ps.setInt(2, detalleVenta.getProductoId());
            ps.setInt(3, detalleVenta.getCantidad());
            ps.setBigDecimal(4, detalleVenta.getPrecioUnitario());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    detalleVenta.setDetalleId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear detalle de venta", e);
        }
    }

    @Override
    public List<DetalleVenta> listarPorVenta(int ventaId) {
        String sql = "SELECT * FROM detalle_venta WHERE venta_id = ?";
        List<DetalleVenta> detalles = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ventaId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    detalles.add(mapear(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar detalles por venta", e);
        }

        return detalles;
    }

    @Override
    public void eliminarPorVenta(int ventaId) {
        String sql = "DELETE FROM detalle_venta WHERE venta_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ventaId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar detalles por venta", e);
        }
    }

    private DetalleVenta mapear(ResultSet rs) throws SQLException {
        DetalleVenta d = new DetalleVenta();
        d.setDetalleId(rs.getInt("detalle_id"));
        d.setVentaId(rs.getInt("venta_id"));
        d.setProductoId(rs.getInt("producto_id"));
        d.setCantidad(rs.getInt("cantidad"));
        d.setPrecioUnitario(rs.getBigDecimal("precio_unitario"));
        return d;
    }
}