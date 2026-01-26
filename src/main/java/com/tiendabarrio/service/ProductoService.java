package com.tiendabarrio.service;

import com.tiendabarrio.model.Producto;
import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {

    // CRUD básico
    void crearProducto(Producto producto);
    Producto buscarProductoPorId(int id);
    List<Producto> listarTodosProductos();
    void actualizarProducto(Producto producto);
    void desactivarProducto(int id);

    // Búsquedas
    List<Producto> buscarProductosPorCategoria(int categoriaId);
    List<Producto> buscarProductosPorProveedor(int proveedorId);
    List<Producto> buscarProductosActivos();
    List<Producto> buscarPorNombre(String nombre);

    // Gestión de stock
    void reducirStock(int productoId, int cantidadAReducir);
    void aumentarStock(int productoId, int cantidadAAumentar);

    // Consultas
    List<Producto> buscarPorRangoPrecio(BigDecimal min, BigDecimal max);
}