package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.ProductoDAO;
import com.tiendabarrio.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public void crear(Producto producto) {
        String sql = "INSERT INTO producto (nombre_producto, precio_producto, stock_producto, activo, categoria_id, proveedor_id) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, producto.getNombreProducto());
            ps.setBigDecimal(2, producto.getPrecioProducto());
            ps.setInt(3, producto.getStockProducto());
            ps.setBoolean(4, producto.isActivo());
            ps.setInt(5, producto.getCategoriaId());
            ps.setInt(6, producto.getProveedorId());
            ps.executeUpdate();

            // CORRECCIÓN: Recuperar ID generado
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    producto.setProductoId(generatedKeys.getInt(1));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear producto", e);
        }
    }

    @Override
    public Producto buscarPorId(int productoId) {
        String sql = "SELECT * FROM producto WHERE producto_id = ?";
        Producto producto = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productoId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto por ID", e);
        }

        return producto;
    }

    @Override
    public List<Producto> listarTodos() {
        String sql = "SELECT * FROM producto";
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar todos los productos", e);
        }

        return productos;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre_producto = ?, precio_producto = ?, stock_producto = ?, activo = ?, categoria_id = ?, proveedor_id = ? WHERE producto_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombreProducto());
            ps.setBigDecimal(2, producto.getPrecioProducto());
            ps.setInt(3, producto.getStockProducto());
            ps.setBoolean(4, producto.isActivo());
            ps.setInt(5, producto.getCategoriaId());
            ps.setInt(6, producto.getProveedorId());
            ps.setInt(7, producto.getProductoId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public void eliminar(int productoId) {
        String sql = "DELETE FROM producto WHERE producto_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productoId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }

    private Producto mapear(ResultSet rs) throws SQLException {
        Producto p = new Producto();
        p.setProductoId(rs.getInt("producto_id"));
        p.setNombreProducto(rs.getString("nombre_producto"));
        p.setPrecioProducto(rs.getBigDecimal("precio_producto"));
        p.setStockProducto(rs.getInt("stock_producto"));
        p.setActivo(rs.getBoolean("activo"));
        p.setCategoriaId(rs.getInt("categoria_id"));
        p.setProveedorId(rs.getInt("proveedor_id"));
        return p;
    }
}