package com.tiendabarrio.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Venta {

    // PRIMARY KEY
    private int ventaId;

    // Datos de la venta
    private LocalDateTime fechaVenta;
    private BigDecimal totalVenta;

    // FOREIGN KEY
    private int usuarioId;

    public Venta() {
    }

    public Venta(int ventaId, LocalDateTime fechaVenta, BigDecimal totalVenta, int usuarioId) {
        this.ventaId = ventaId;
        this.fechaVenta = fechaVenta;
        this.totalVenta = totalVenta;
        this.usuarioId = usuarioId;
    }

    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public LocalDateTime getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDateTime fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "ventaId=" + ventaId +
                ", fechaVenta=" + fechaVenta +
                ", totalVenta=" + totalVenta +
                '}';
    }
}