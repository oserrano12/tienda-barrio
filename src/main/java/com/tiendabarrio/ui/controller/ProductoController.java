package com.tiendabarrio.ui.controller;

import com.tiendabarrio.model.Producto;
import com.tiendabarrio.service.ProductoService;
import com.tiendabarrio.service.impl.ProductoServiceImpl;
import com.tiendabarrio.ui.view.ProductoView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.List;

public class ProductoController {
    private final ProductoView view;
    private final Stage stage;
    private final ProductoService productoService;
    private final MainController mainController;

    public ProductoController(Stage stage, MainController mainController) {
        this.stage = stage;
        this.mainController = mainController;
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
    }

    private void cargarDatos() {
        try {
            // Obtener productos de la base de datos real
            List<Producto> lista = productoService.listarTodosProductos();
            ObservableList<Producto> observableList = FXCollections.observableArrayList(lista);

            // Cargar en la tabla
            view.cargarProductos(observableList);

        } catch (Exception e) {
            System.err.println("Error cargando productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrar() {
        view.mostrar(stage);
    }
}