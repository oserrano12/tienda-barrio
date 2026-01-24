package com.tiendabarrio.dao;

import com.tiendabarrio.model.Venta;
import java.util.List;

public interface VentaDAO {

    void crear(Venta venta);

    Venta buscarPorId(int ventaId);

    List<Venta> listarTodos();

    List<Venta> listarPorUsuario(int usuarioId);

    void eliminar(int ventaId);
}