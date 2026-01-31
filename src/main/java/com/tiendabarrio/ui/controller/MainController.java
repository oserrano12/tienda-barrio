package com.tiendabarrio.ui.controller;

import com.tiendabarrio.ui.view.MainView;
import com.tiendabarrio.util.SesionUsuario;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class MainController {
    private final MainView view;
    private final Stage stage;
    private final String nombreUsuario;

    public MainController(Stage stage, String nombreUsuario) {
        this.stage = stage;
        this.nombreUsuario = nombreUsuario;
        this.view = new MainView();

        configurarEventos();
    }

    private void abrirModuloProductos() {
        // Abrir módulo de productos como ventana separada (encima)
        Stage productoStage = new Stage();
        ProductoController productoController = new ProductoController(productoStage, this);
        productoController.mostrar();
    }

    private void configurarEventos() {
        // Botón Productos (preparado para futura funcionalidad)
        view.getBtnProductos().setOnAction(e -> abrirModuloProductos());

        // Botón Ventas (preparado para futura funcionalidad)
        view.getBtnVentas().setOnAction(e -> {
            mostrarMensaje("Módulo de Ventas", "Aquí irá el punto de venta");
        });

        // Botón Usuarios (preparado para futura funcionalidad)
        view.getBtnUsuarios().setOnAction(e -> {
            mostrarMensaje("Módulo de Usuarios", "Aquí irá la gestión de usuarios");
        });

        // Botón Cerrar Sesión
        view.getBtnCerrarSesion().setOnAction(e -> cerrarSesion());
    }

    private void mostrarMensaje(String titulo, String contenido) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido + "\n\n(En desarrollo)");
        alert.showAndWait();
    }

    private void cerrarSesion() {
        // Cerrar sesión en el sistema
        SesionUsuario.getInstancia().cerrarSesion();

        // Volver a la pantalla de login
        stage.close();

        // Crear nuevo stage para login
        Platform.runLater(() -> {
            Stage newStage = new Stage();
            LoginController loginController = new LoginController(newStage);
            loginController.mostrar();
        });
    }

    public void mostrar() {
        view.mostrar(stage, nombreUsuario);
    }
}