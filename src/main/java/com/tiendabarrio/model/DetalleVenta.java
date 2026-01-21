package com.tiendabarrio.model;

import java.math.BigDecimal;

public class DetalleVenta {

    // PRIMARY KEY
    private int detalleId;

    // Datos del detalle
    private int cantidad;
    private BigDecimal precioUnitario;

    // FOREIGN KEYS
    private int ventaId;
    private int productoId;

    public DetalleVenta() {
    }

    public DetalleVenta(int detalleId, int cantidad, BigDecimal precioUnitario,
                        int ventaId, int productoId) {
        this.detalleId = detalleId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.ventaId = ventaId;
        this.productoId = productoId;
    }

    public int getDetalleId() {
        return detalleId;
    }

    public void setDetalleId(int detalleId) {
        this.detalleId = detalleId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "detalleId=" + detalleId +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                '}';
    }
}
