package com.tiendabarrio.dao;

import com.tiendabarrio.model.DetalleVenta;
import java.sql.Connection;
import java.util.List;

public interface DetalleVentaDAO {
    void crear(DetalleVenta detalleVenta);
    void crear(DetalleVenta detalleVenta, Connection conn);
    List<DetalleVenta> listarPorVenta(int ventaId);
    void eliminarPorVenta(int ventaId);
}