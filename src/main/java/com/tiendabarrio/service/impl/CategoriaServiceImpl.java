package com.tiendabarrio.service.impl;

import com.tiendabarrio.dao.CategoriaDAO;
import com.tiendabarrio.dao.impl.CategoriaDAOImpl;
import com.tiendabarrio.model.Categoria;
import com.tiendabarrio.service.CategoriaService;
import java.util.List;

public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaDAO categoriaDAO;

    public CategoriaServiceImpl() {
        this.categoriaDAO = new CategoriaDAOImpl();
    }

    public CategoriaServiceImpl(CategoriaDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    @Override
    public void crearCategoria(Categoria categoria) {
        validarCategoria(categoria);

        if (existeCategoriaConNombre(categoria.getNombreCategoria())) {
            throw new RuntimeException("Categoría ya existe: " + categoria.getNombreCategoria());
        }

        categoriaDAO.crear(categoria);
    }

    @Override
    public Categoria buscarCategoriaPorId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        Categoria categoria = categoriaDAO.buscarPorId(id);
        if (categoria == null) throw new RuntimeException("Categoría no encontrada");
        return categoria;
    }

    @Override
    public List<Categoria> listarTodasCategorias() {
        return categoriaDAO.listarTodos();
    }

    @Override
    public void actualizarCategoria(Categoria categoria) {
        validarCategoria(categoria);
        if (categoria.getCategoriaId() <= 0) throw new IllegalArgumentException("ID inválido");

        Categoria existente = categoriaDAO.buscarPorId(categoria.getCategoriaId());
        if (existente == null) throw new RuntimeException("Categoría no encontrada");

        categoriaDAO.actualizar(categoria);
    }

    @Override
    public void eliminarCategoria(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");

        // TODO: Verificar que no tenga productos asociados
        // (Implementar cuando tengamos ProductoService)

        categoriaDAO.eliminar(id);
    }

    @Override
    public boolean existeCategoriaConNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre vacío");

        List<Categoria> categorias = categoriaDAO.listarTodos();
        return categorias.stream()
                .anyMatch(c -> c.getNombreCategoria().equalsIgnoreCase(nombre.trim()));
    }

    private void validarCategoria(Categoria categoria) {
        if (categoria == null) throw new IllegalArgumentException("Categoría nula");
        if (categoria.getNombreCategoria() == null || categoria.getNombreCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre categoría obligatorio");
        }
    }
}