package com.tiendabarrio.model;

import java.math.BigDecimal;

public class Producto {

    // PRIMARY KEY
    private int productoId;

    // Datos del producto
    private String nombreProducto;
    private BigDecimal precioProducto;
    private int stockProducto;
    private boolean activo;

    // FOREIGN KEYS
    private int categoriaId;
    private int proveedorId;

    public Producto() {
    }

    public Producto(int productoId, String nombreProducto, BigDecimal precioProducto,
                    int stockProducto, boolean activo, int categoriaId, int proveedorId) {
        this.productoId = productoId;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.stockProducto = stockProducto;
        this.activo = activo;
        this.categoriaId = categoriaId;
        this.proveedorId = proveedorId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(BigDecimal precioProducto) {
        this.precioProducto = precioProducto;
    }

    public int getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(int stockProducto) {
        this.stockProducto = stockProducto;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(int categoriaId) {
        this.categoriaId = categoriaId;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "productoId=" + productoId +
                ", nombreProducto='" + nombreProducto + '\'' +
                ", precioProducto=" + precioProducto +
                ", stockProducto=" + stockProducto +
                ", activo=" + activo +
                '}';
    }
}
