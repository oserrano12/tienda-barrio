package com.tiendabarrio.ui.controller;

import com.tiendabarrio.model.Producto;
import com.tiendabarrio.service.ProductoService;
import com.tiendabarrio.service.impl.ProductoServiceImpl;
import com.tiendabarrio.ui.view.InventarioView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class InventarioController {

    private final InventarioView view;
    private final Stage stage;
    private final ProductoService productoService;
    private ObservableList<Producto> todosLosProductos;
    private Producto productoSeleccionado;

    public InventarioController(Stage stage) {
        this.stage = stage;
        this.view = new InventarioView();
        this.productoService = new ProductoServiceImpl();

        configurarEventos();
        cargarProductos();
    }

    private void configurarEventos() {
        // Seleccionar producto de la tabla
        view.getTablaProductos().getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        productoSeleccionado = newSelection;
                        view.mostrarProductoSeleccionado(newSelection);
                    }
                }
        );

        // Buscar productos
        view.getBtnBuscar().setOnAction(e -> {
            String filtro = view.getTxtBuscar().getText().toLowerCase().trim();
            if (filtro.isEmpty()) {
                view.cargarProductos(todosLosProductos);
            } else {
                List<Producto> filtrados = todosLosProductos.stream()
                        .filter(p -> p.getNombreProducto().toLowerCase().contains(filtro))
                        .collect(Collectors.toList());
                view.cargarProductos(FXCollections.observableArrayList(filtrados));
            }
        });

        // Limpiar búsqueda
        view.getBtnLimpiar().setOnAction(e -> {
            view.getTxtBuscar().clear();
            view.cargarProductos(todosLosProductos);
            view.limpiarSeleccion();
            productoSeleccionado = null;
        });

        // Aumentar stock
        view.getBtnAumentarStock().setOnAction(e -> {
            if (productoSeleccionado == null) return;

            int cantidad = view.getSpnCantidadStock().getValue();
            try {
                productoService.aumentarStock(productoSeleccionado.getProductoId(), cantidad);
                recargarProductoSeleccionado();
                System.out.println("Stock aumentado en " + cantidad);
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        });

        // Reducir stock
        view.getBtnReducirStock().setOnAction(e -> {
            if (productoSeleccionado == null) return;

            int cantidad = view.getSpnCantidadStock().getValue();
            try {
                productoService.reducirStock(productoSeleccionado.getProductoId(), cantidad);
                recargarProductoSeleccionado();
                System.out.println("Stock reducido en " + cantidad);
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        });

        // Activar/Desactivar
        view.getBtnActivarDesactivar().setOnAction(e -> {
            if (productoSeleccionado == null) return;

            try {
                if (productoSeleccionado.isActivo()) {
                    productoService.desactivarProducto(productoSeleccionado.getProductoId());
                    System.out.println("Producto desactivado");
                } else {
                    // Reactivar (usamos actualizar)
                    productoSeleccionado.setActivo(true);
                    productoService.actualizarProducto(productoSeleccionado);
                    System.out.println("Producto activado");
                }
                recargarProductoSeleccionado();
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        });

        // Guardar cambios (nombre y precio)
        view.getBtnGuardar().setOnAction(e -> {
            if (productoSeleccionado == null) return;

            try {
                String nuevoNombre = view.getTxtNombre().getText().trim();
                String nuevoPrecioStr = view.getTxtPrecio().getText().trim();

                if (nuevoNombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacío");
                    return;
                }

                BigDecimal nuevoPrecio = new BigDecimal(nuevoPrecioStr);

                productoSeleccionado.setNombreProducto(nuevoNombre);
                productoSeleccionado.setPrecioProducto(nuevoPrecio);

                productoService.actualizarProducto(productoSeleccionado);
                recargarProductoSeleccionado();
                cargarProductos(); // Recargar tabla

                System.out.println("Cambios guardados");
            } catch (NumberFormatException ex) {
                System.out.println("Precio inválido");
            } catch (Exception ex) {
                System.err.println("Error: " + ex.getMessage());
            }
        });

        // Volver al menú
        view.getBtnVolver().setOnAction(e -> stage.close());
    }

    private void cargarProductos() {
        try {
            List<Producto> lista = productoService.listarTodosProductos();
            todosLosProductos = FXCollections.observableArrayList(lista);
            view.cargarProductos(todosLosProductos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recargarProductoSeleccionado() {
        if (productoSeleccionado != null) {
            Producto actualizado = productoService.buscarProductoPorId(productoSeleccionado.getProductoId());
            productoSeleccionado = actualizado;
            view.mostrarProductoSeleccionado(actualizado);
            cargarProductos(); // Actualizar tabla también
        }
    }

    public void mostrar() {
        view.mostrar(stage);
    }
}