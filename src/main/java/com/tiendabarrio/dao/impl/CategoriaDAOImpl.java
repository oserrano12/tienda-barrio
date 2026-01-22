package com.tiendabarrio.dao.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.CategoriaDAO;
import com.tiendabarrio.model.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAOImpl implements CategoriaDAO {

    @Override
    public void crear(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre_categoria, descripcion_categoria) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombreCategoria());
            ps.setString(2, categoria.getDescripcionCategoria());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear categoria", e);
        }
    }

    @Override
    public Categoria buscarPorId(int id) {
        String sql = "SELECT * FROM categoria WHERE categoria_id = ?";
        Categoria categoria = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoria = mapearCategoria(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar categoria por id", e);
        }

        return categoria;
    }

    @Override
    public Categoria buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM categoria WHERE nombre_categoria = ?";
        Categoria categoria = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nombre);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoria = mapearCategoria(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar categoria por nombre", e);
        }

        return categoria;
    }

    @Override
    public List<Categoria> listarTodas() {
        String sql = "SELECT * FROM categoria";
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categorias.add(mapearCategoria(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar categorias", e);
        }

        return categorias;
    }

    @Override
    public void actualizar(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre_categoria = ?, descripcion_categoria = ? WHERE categoria_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombreCategoria());
            ps.setString(2, categoria.getDescripcionCategoria());
            ps.setInt(3, categoria.getCategoriaId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar categoria", e);
        }
    }

    @Override
    public void eliminar(int id) {
        String sql = "DELETE FROM categoria WHERE categoria_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar categoria", e);
        }
    }

    private Categoria mapearCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setCategoriaId(rs.getInt("categoria_id"));
        categoria.setNombreCategoria(rs.getString("nombre_categoria"));
        categoria.setDescripcionCategoria(rs.getString("descripcion_categoria"));
        return categoria;
    }
}