package com.tiendabarrio.model;

public class Categoria {
    // PRIMARY KEY
    private int categoriaId;

    // Datos de la categoria
    private String nombreCategoria;
    private String descripcionCategoria;

    public Categoria() {
    }

    public Categoria(int categoriaId, String nombreCategoria, String descripcionCategoria) {
        this.categoriaId = categoriaId;
        this.nombreCategoria = nombreCategoria;
        this.descripcionCategoria = descripcionCategoria;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }

    public String getDescripcionCategoria() {
        return descripcionCategoria;
    }

    public void setDescripcionCategoria(String descripcionCategoria) {
        this.descripcionCategoria = descripcionCategoria;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "categoriaId=" + categoriaId +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                '}';
    }
}