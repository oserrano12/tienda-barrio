package com.tiendabarrio.service;

import com.tiendabarrio.model.DetalleVenta;
import com.tiendabarrio.model.Venta;
import java.math.BigDecimal;
import java.util.List;

public interface VentaService {
    Venta crearVenta(Venta venta, List<DetalleVenta> detalles);
    Venta buscarVentaPorId(int id);
    List<Venta> listarTodasVentas();
    List<Venta> listarVentasPorUsuario(int usuarioId);
    List<DetalleVenta> obtenerDetallesVenta(int ventaId);
    BigDecimal calcularTotalVenta(List<DetalleVenta> detalles);
    void cancelarVenta(int ventaId);
}