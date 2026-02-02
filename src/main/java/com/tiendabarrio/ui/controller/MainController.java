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
    private final boolean esAdmin;

    public MainController(Stage stage, String nombreUsuario) {
        this.stage = stage;
        this.nombreUsuario = nombreUsuario;
        this.esAdmin = SesionUsuario.getInstancia().tieneRol("ADMIN");

        this.view = new MainView();

        configurarVistaSegunRol();
        configurarEventos();
    }

    private void configurarVistaSegunRol() {
        // Si NO es admin, ocultar botones de administración
        if (!esAdmin) {
            view.getBtnInventario().setVisible(false);
            view.getBtnInventario().setManaged(false);

            view.getBtnUsuariosAdmin().setVisible(false);
            view.getBtnUsuariosAdmin().setManaged(false);

            System.out.println("Acceso como VENDEDOR - Funciones limitadas");
        } else {
            System.out.println("Acceso como ADMIN - Todas las funciones disponibles");
        }
    }

    private void configurarEventos() {
        // Productos: TODOS pueden ver
        view.getBtnProductos().setOnAction(e -> abrirModuloProductos());

        // Ventas: TODOS pueden acceder
        view.getBtnVentas().setOnAction(e -> abrirModuloVentas());

        // Inventario: Solo ADMIN
        view.getBtnInventario().setOnAction(e -> {
            if (esAdmin) {
                abrirModuloInventario();
            } else {
                mostrarAccesoDenegado("Inventario");
            }
        });

        // Usuarios (gestión): Solo ADMIN
        view.getBtnUsuariosAdmin().setOnAction(e -> {
            if (esAdmin) {
                abrirModuloUsuarios();
            } else {
                mostrarAccesoDenegado("Gestión de Usuarios");
            }
        });

        // Cerrar sesión: TODOS
        view.getBtnCerrarSesion().setOnAction(e -> cerrarSesion());
    }

    private void mostrarAccesoDenegado(String modulo) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Acceso Denegado");
        alert.setHeaderText(null);
        alert.setContentText("No tienes permisos para acceder a: " + modulo + "\n\nContacta al administrador.");
        alert.showAndWait();
    }

    private void abrirModuloProductos() {
        Stage productoStage = new Stage();
        ProductoController productoController = new ProductoController(productoStage, this);
        productoController.mostrar();
    }

    private void abrirModuloVentas() {
        Stage ventaStage = new Stage();
        VentaController ventaController = new VentaController(ventaStage);
        ventaController.mostrar();
    }

    private void abrirModuloInventario() {
        Stage inventarioStage = new Stage();
        InventarioController inventarioController = new InventarioController(inventarioStage);
        inventarioController.mostrar();
    }

    private void abrirModuloUsuarios() {
        Stage usuarioStage = new Stage();
        UsuarioAdminController usuarioAdminController = new UsuarioAdminController(usuarioStage);
        usuarioAdminController.mostrar();
    }

    private void cerrarSesion() {
        SesionUsuario.getInstancia().cerrarSesion();
        stage.close();

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