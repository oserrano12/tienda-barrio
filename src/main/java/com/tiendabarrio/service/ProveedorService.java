package com.tiendabarrio.service;

import com.tiendabarrio.model.Proveedor;
import java.util.List;

public interface ProveedorService {
    void crearProveedor(Proveedor proveedor);
    Proveedor buscarProveedorPorId(int id);
    List<Proveedor> listarTodosProveedores();
    void actualizarProveedor(Proveedor proveedor);
    void eliminarProveedor(int id);
    Proveedor buscarProveedorPorEmail(String email);
}