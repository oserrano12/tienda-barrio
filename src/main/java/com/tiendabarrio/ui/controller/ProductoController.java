package com.tiendabarrio.ui.controller;

import com.tiendabarrio.model.Producto;
import com.tiendabarrio.service.ProductoService;
import com.tiendabarrio.service.impl.ProductoServiceImpl;
import com.tiendabarrio.ui.view.ProductoView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.List;
import java.util.stream.Collectors;

public class ProductoController {
    private final ProductoView view;
    private final Stage stage;
    private final ProductoService productoService;
    private ObservableList<Producto> todosLosProductos;

    public ProductoController(Stage stage, MainController mainController) {
        this.stage = stage;
        this.view = new ProductoView();
        this.productoService = new ProductoServiceImpl();

        configurarEventos();
        cargarDatos();
    }

    private void configurarEventos() {
        // Botón Volver: regresar a la ventana principal
        view.getBtnVolver().setOnAction(e -> {
            stage.close();
        });

        // Botón Actualizar: recargar datos de la BD
        view.getBtnActualizar().setOnAction(e -> cargarDatos());

        // Botón Buscar: filtrar productos
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

        // Botón Limpiar: mostrar todos nuevamente
        view.getBtnLimpiar().setOnAction(e -> {
            view.getTxtBuscar().clear();
            view.cargarProductos(todosLosProductos);
        });
    }

    private void cargarDatos() {
        try {
            // Obtener productos de la base de datos real
            List<Producto> lista = productoService.listarTodosProductos();
            todosLosProductos = FXCollections.observableArrayList(lista);

            // Cargar en la tabla
            view.cargarProductos(todosLosProductos);

        } catch (Exception e) {
            System.err.println("Error cargando productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrar() {
        view.mostrar(stage);
    }
}