package com.tiendabarrio.dao;

import com.tiendabarrio.model.DetalleVenta;
import java.util.List;

public interface DetalleVentaDAO {

    void crear(DetalleVenta detalleVenta);

    List<DetalleVenta> listarPorVenta(int ventaId);

    void eliminarPorVenta(int ventaId);
}