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
        String sql = """
            INSERT INTO producto
            (nombre_producto, precio_producto, stock_producto, activo, categoria_id, proveedor_id)
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombreProducto());
            ps.setBigDecimal(2, producto.getPrecioProducto());
            ps.setInt(3, producto.getStockProducto());
            ps.setBoolean(4, producto.isActivo());
            ps.setInt(5, producto.getCategoriaId());
            ps.setInt(6, producto.getProveedorId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear producto", e);
        }
    }

    @Override
    public Producto buscarPorId(int id) {
        String sql = "SELECT * FROM producto WHERE producto_id = ?";
        Producto producto = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    producto = mapearProducto(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto por id", e);
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
                productos.add(mapearProducto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos", e);
        }

        return productos;
    }

    @Override
    public List<Producto> listarActivos() {
        String sql = "SELECT * FROM producto WHERE activo = true";
        List<Producto> productos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                productos.add(mapearProducto(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar productos activos", e);
        }

        return productos;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = """
            UPDATE producto
            SET nombre_producto = ?, precio_producto = ?, stock_producto = ?,
                categoria_id = ?, proveedor_id = ?
            WHERE producto_id = ?
        """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, producto.getNombreProducto());
            ps.setBigDecimal(2, producto.getPrecioProducto());
            ps.setInt(3, producto.getStockProducto());
            ps.setInt(4, producto.getCategoriaId());
            ps.setInt(5, producto.getProveedorId());
            ps.setInt(6, producto.getProductoId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public void cambiarEstado(int id, boolean activo) {
        String sql = "UPDATE producto SET activo = ? WHERE producto_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, activo);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al cambiar estado del producto", e);
        }
    }

    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setProductoId(rs.getInt("producto_id"));
        producto.setNombreProducto(rs.getString("nombre_producto"));
        producto.setPrecioProducto(rs.getBigDecimal("precio_producto"));
        producto.setStockProducto(rs.getInt("stock_producto"));
        producto.setActivo(rs.getBoolean("activo"));
        producto.setCategoriaId(rs.getInt("categoria_id"));
        producto.setProveedorId(rs.getInt("proveedor_id"));
        return producto;
    }
}