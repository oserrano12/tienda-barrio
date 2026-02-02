package com.tiendabarrio.ui.controller;

import com.tiendabarrio.model.DetalleVenta;
import com.tiendabarrio.model.Producto;
import com.tiendabarrio.model.Venta;
import com.tiendabarrio.service.ProductoService;
import com.tiendabarrio.service.VentaService;
import com.tiendabarrio.service.impl.ProductoServiceImpl;
import com.tiendabarrio.service.impl.VentaServiceImpl;
import com.tiendabarrio.ui.view.NuevaVentaView;
import com.tiendabarrio.util.SesionUsuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class NuevaVentaController {

    private final NuevaVentaView view;
    private final Stage stage;
    private final ProductoService productoService;
    private final VentaService ventaService;
    private final ObservableList<NuevaVentaView.ItemCarrito> carrito;

    public NuevaVentaController(Stage stage) {
        this.stage = stage;
        this.view = new NuevaVentaView();
        this.productoService = new ProductoServiceImpl();
        this.ventaService = new VentaServiceImpl();
        this.carrito = FXCollections.observableArrayList();

        configurarEventos();
        cargarProductos();
        view.getTablaCarrito().setItems(carrito);
    }

    private void configurarEventos() {
        // Agregar producto al carrito
        view.getBtnAgregar().setOnAction(e -> agregarAlCarrito());

        // Finalizar venta
        view.getBtnFinalizar().setOnAction(e -> finalizarVenta());

        // Cancelar
        view.getBtnCancelar().setOnAction(e -> stage.close());
    }

    private void cargarProductos() {
        // Cargar solo productos activos con stock > 0
        List<Producto> productos = productoService.buscarProductosActivos();
        view.getCmbProductos().getItems().addAll(productos);

        // Mostrar nombre del producto en el ComboBox
        view.getCmbProductos().setCellFactory(param -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getNombreProducto() + " - $" + formatearPesos(item.getPrecioProducto())
                            + " (Stock: " + item.getStockProducto() + ")");
                }
            }
        });

        view.getCmbProductos().setButtonCell(new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Producto item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Seleccionar producto...");
                } else {
                    setText(item.getNombreProducto());
                }
            }
        });
    }

    private void agregarAlCarrito() {
        Producto seleccionado = view.getCmbProductos().getValue();
        int cantidad = view.getSpnCantidad().getValue();

        if (seleccionado == null) {
            System.out.println("Selecciona un producto primero");
            return;
        }

        // Verificar stock suficiente
        if (cantidad > seleccionado.getStockProducto()) {
            System.out.println("Stock insuficiente. Disponible: " + seleccionado.getStockProducto());
            return;
        }

        // Verificar si ya está en el carrito (sumar cantidad)
        for (NuevaVentaView.ItemCarrito item : carrito) {
            if (item.getProducto().getProductoId() == seleccionado.getProductoId()) {
                System.out.println("Producto ya en carrito. Elimínalo y vuelve a agregar.");
                return;
            }
        }

        // Agregar al carrito
        carrito.add(new NuevaVentaView.ItemCarrito(seleccionado, cantidad));
        actualizarTotal();

        // Resetear selección
        view.getCmbProductos().setValue(null);
        view.getSpnCantidad().getValueFactory().setValue(1);
    }

    private void actualizarTotal() {
        BigDecimal total = BigDecimal.ZERO;
        for (NuevaVentaView.ItemCarrito item : carrito) {
            total = total.add(item.getSubtotal());
        }
        view.getLblTotal().setText("Total: " + formatearPesos(total));
    }

    private void finalizarVenta() {
        if (carrito.isEmpty()) {
            System.out.println("El carrito está vacío");
            return;
        }

        try {
            // Crear objeto Venta
            Venta venta = new Venta();
            venta.setUsuarioId(SesionUsuario.getInstancia().getUsuarioActual().getUsuarioId());

            // Crear lista de detalles
            List<DetalleVenta> detalles = new ArrayList<>();
            for (NuevaVentaView.ItemCarrito item : carrito) {
                DetalleVenta detalle = new DetalleVenta();
                detalle.setProductoId(item.getProducto().getProductoId());
                detalle.setCantidad(item.getCantidad());
                detalle.setPrecioUnitario(item.getPrecioUnitario());
                detalles.add(detalle);
            }

            // Guardar en BD (esto reduce stock automáticamente por tu transacción)
            ventaService.crearVenta(venta, detalles);

            System.out.println("✅ Venta creada exitosamente. ID: " + venta.getVentaId());
            stage.close();

        } catch (Exception e) {
            System.err.println("❌ Error al crear venta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Formato pesos colombianos: 4500 → $4.500
    private String formatearPesos(BigDecimal valor) {
        String numero = valor.toBigInteger().toString();
        StringBuilder resultado = new StringBuilder();

        int contador = 0;
        for (int i = numero.length() - 1; i >= 0; i--) {
            if (contador > 0 && contador % 3 == 0) {
                resultado.insert(0, '.');
            }
            resultado.insert(0, numero.charAt(i));
            contador++;
        }

        return "$" + resultado.toString();
    }

    public void mostrar() {
        view.mostrar(stage);
    }
}