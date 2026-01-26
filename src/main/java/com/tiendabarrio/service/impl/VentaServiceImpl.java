package com.tiendabarrio.service.impl;

import com.tiendabarrio.config.ConnectionFactory;
import com.tiendabarrio.dao.DetalleVentaDAO;
import com.tiendabarrio.dao.VentaDAO;
import com.tiendabarrio.dao.impl.DetalleVentaDAOImpl;
import com.tiendabarrio.dao.impl.VentaDAOImpl;
import com.tiendabarrio.model.DetalleVenta;
import com.tiendabarrio.model.Venta;
import com.tiendabarrio.service.ProductoService;
import com.tiendabarrio.service.VentaService;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class VentaServiceImpl implements VentaService {

    private final VentaDAO ventaDAO;
    private final DetalleVentaDAO detalleVentaDAO;
    private final ProductoService productoService;

    public VentaServiceImpl() {
        this.ventaDAO = new VentaDAOImpl();
        this.detalleVentaDAO = new DetalleVentaDAOImpl();
        this.productoService = new ProductoServiceImpl();
    }

    @Override
    public Venta crearVenta(Venta venta, List<DetalleVenta> detalles) {
        validarVenta(venta);
        validarDetalles(detalles);

        Connection conn = null;

        try {
            conn = ConnectionFactory.getConnection();
            conn.setAutoCommit(false);

            BigDecimal total = calcularTotalVenta(detalles);
            venta.setTotalVenta(total);
            venta.setFechaVenta(LocalDateTime.now());

            ventaDAO.crear(venta, conn);

            for (DetalleVenta detalle : detalles) {
                productoService.reducirStock(detalle.getProductoId(), detalle.getCantidad());
                detalle.setVentaId(venta.getVentaId());
                detalleVentaDAO.crear(detalle, conn);
            }

            conn.commit();
            return venta;

        } catch (Exception e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw new RuntimeException("Error al crear venta: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public Venta buscarVentaPorId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        Venta venta = ventaDAO.buscarPorId(id);
        if (venta == null) throw new RuntimeException("Venta no encontrada");
        return venta;
    }

    @Override
    public List<Venta> listarTodasVentas() {
        return ventaDAO.listarTodos();
    }

    @Override
    public List<Venta> listarVentasPorUsuario(int usuarioId) {
        if (usuarioId <= 0) throw new IllegalArgumentException("ID usuario inválido");
        return ventaDAO.listarPorUsuario(usuarioId);
    }

    @Override
    public List<DetalleVenta> obtenerDetallesVenta(int ventaId) {
        if (ventaId <= 0) throw new IllegalArgumentException("ID venta inválido");
        return detalleVentaDAO.listarPorVenta(ventaId);
    }

    @Override
    public BigDecimal calcularTotalVenta(List<DetalleVenta> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("Venta sin productos");
        }

        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVenta detalle : detalles) {
            BigDecimal subtotal = detalle.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalle.getCantidad()));
            total = total.add(subtotal);
        }

        return total;
    }

    @Override
    public void cancelarVenta(int ventaId) {
        if (ventaId <= 0) throw new IllegalArgumentException("ID inválido");

        List<DetalleVenta> detalles = detalleVentaDAO.listarPorVenta(ventaId);

        for (DetalleVenta detalle : detalles) {
            productoService.aumentarStock(detalle.getProductoId(), detalle.getCantidad());
        }

        ventaDAO.eliminar(ventaId);
    }

    private void validarVenta(Venta venta) {
        if (venta == null) throw new IllegalArgumentException("Venta nula");
        if (venta.getUsuarioId() <= 0) throw new IllegalArgumentException("Usuario inválido");
    }

    private void validarDetalles(List<DetalleVenta> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("Venta sin productos");
        }

        for (DetalleVenta detalle : detalles) {
            if (detalle.getProductoId() <= 0) throw new IllegalArgumentException("Producto inválido");
            if (detalle.getCantidad() <= 0) throw new IllegalArgumentException("Cantidad debe ser > 0");
            if (detalle.getPrecioUnitario() == null ||
                    detalle.getPrecioUnitario().compareTo(BigDecimal.ZERO) <= 0) {
                throw new IllegalArgumentException("Precio unitario inválido");
            }
        }
    }
}