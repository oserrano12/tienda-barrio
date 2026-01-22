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
            throw new RuntimeException(e);
        }
    }

    @Override
    public Categoria buscarPorId(int categoriaId) {
        String sql = "SELECT * FROM categoria WHERE categoria_id = ?";
        Categoria categoria = null;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoriaId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    categoria = mapear(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return categoria;
    }

    @Override
    public List<Categoria> listarTodos() {
        String sql = "SELECT * FROM categoria";
        List<Categoria> categorias = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categorias.add(mapear(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public void eliminar(int categoriaId) {
        String sql = "DELETE FROM categoria WHERE categoria_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, categoriaId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Categoria mapear(ResultSet rs) throws SQLException {
        Categoria c = new Categoria();
        c.setCategoriaId(rs.getInt("categoria_id"));
        c.setNombreCategoria(rs.getString("nombre_categoria"));
        c.setDescripcionCategoria(rs.getString("descripcion_categoria"));
        return c;
    }
}