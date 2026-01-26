package com.tiendabarrio.service.impl;

import com.tiendabarrio.dao.ProductoDAO;
import com.tiendabarrio.dao.impl.ProductoDAOImpl;
import com.tiendabarrio.model.Producto;
import com.tiendabarrio.service.ProductoService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ProductoServiceImpl implements ProductoService {

    private final ProductoDAO productoDAO;

    public ProductoServiceImpl() {
        this.productoDAO = new ProductoDAOImpl();
    }

    public ProductoServiceImpl(ProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    public void crearProducto(Producto producto) {
        validarProducto(producto);
        if (producto.getPrecioProducto().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Precio debe ser > 0");
        }
        if (producto.getStockProducto() < 0) {
            throw new IllegalArgumentException("Stock no negativo");
        }
        producto.setActivo(true);
        productoDAO.crear(producto);
    }

    @Override
    public Producto buscarProductoPorId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        Producto producto = productoDAO.buscarPorId(id);
        if (producto == null) throw new RuntimeException("Producto no encontrado");
        return producto;
    }

    @Override
    public List<Producto> listarTodosProductos() {
        return productoDAO.listarTodos();
    }

    @Override
    public void actualizarProducto(Producto producto) {
        validarProducto(producto);
        if (producto.getProductoId() <= 0) throw new IllegalArgumentException("ID inválido");
        Producto existente = productoDAO.buscarPorId(producto.getProductoId());
        if (existente == null) throw new RuntimeException("Producto no encontrado");
        productoDAO.actualizar(producto);
    }

    @Override
    public void desactivarProducto(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        Producto producto = productoDAO.buscarPorId(id);
        if (producto == null) throw new RuntimeException("Producto no encontrado");
        producto.setActivo(false);
        productoDAO.actualizar(producto);
    }

    @Override
    public List<Producto> buscarProductosPorCategoria(int categoriaId) {
        if (categoriaId <= 0) throw new IllegalArgumentException("Categoría inválida");
        return productoDAO.listarTodos().stream()
                .filter(p -> p.getCategoriaId() == categoriaId && p.isActivo())
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> buscarProductosPorProveedor(int proveedorId) {
        if (proveedorId <= 0) throw new IllegalArgumentException("Proveedor inválido");
        return productoDAO.listarTodos().stream()
                .filter(p -> p.getProveedorId() == proveedorId && p.isActivo())
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> buscarProductosActivos() {
        return productoDAO.listarTodos().stream()
                .filter(Producto::isActivo)
                .collect(Collectors.toList());
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre vacío");
        String busqueda = nombre.toLowerCase().trim();
        return productoDAO.listarTodos().stream()
                .filter(p -> p.isActivo() && p.getNombreProducto().toLowerCase().contains(busqueda))
                .collect(Collectors.toList());
    }

    @Override
    public void reducirStock(int productoId, int cantidadAReducir) {
        validarCantidad(cantidadAReducir);
        Producto producto = productoDAO.buscarPorId(productoId);
        if (producto == null) throw new RuntimeException("Producto no encontrado");
        if (!producto.isActivo()) throw new RuntimeException("Producto inactivo");
        int stockActual = producto.getStockProducto();
        if (cantidadAReducir > stockActual) {
            throw new RuntimeException(String.format(
                    "Stock insuficiente: %s (Stock: %d, Solicitado: %d)",
                    producto.getNombreProducto(), stockActual, cantidadAReducir
            ));
        }
        producto.setStockProducto(stockActual - cantidadAReducir);
        productoDAO.actualizar(producto);
    }

    @Override
    public void aumentarStock(int productoId, int cantidadAAumentar) {
        validarCantidad(cantidadAAumentar);
        Producto producto = productoDAO.buscarPorId(productoId);
        if (producto == null) throw new RuntimeException("Producto no encontrado");
        if (!producto.isActivo()) throw new RuntimeException("Producto inactivo");
        producto.setStockProducto(producto.getStockProducto() + cantidadAAumentar);
        productoDAO.actualizar(producto);
    }

    @Override
    public List<Producto> buscarPorRangoPrecio(BigDecimal min, BigDecimal max) {
        if (min == null || max == null) throw new IllegalArgumentException("Límites no nulos");
        if (min.compareTo(BigDecimal.ZERO) < 0 || max.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Precios no negativos");
        }
        if (min.compareTo(max) > 0) throw new IllegalArgumentException("Mínimo > máximo");
        return productoDAO.listarTodos().stream()
                .filter(p -> p.isActivo() &&
                        p.getPrecioProducto().compareTo(min) >= 0 &&
                        p.getPrecioProducto().compareTo(max) <= 0)
                .collect(Collectors.toList());
    }

    private void validarProducto(Producto producto) {
        if (producto == null) throw new IllegalArgumentException("Producto nulo");
        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre obligatorio");
        }
        if (producto.getCategoriaId() <= 0) throw new IllegalArgumentException("Categoría inválida");
        if (producto.getProveedorId() <= 0) throw new IllegalArgumentException("Proveedor inválido");
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva");
    }
}