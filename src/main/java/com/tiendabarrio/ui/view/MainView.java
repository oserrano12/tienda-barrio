package com.tiendabarrio.ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainView {
    private BorderPane root;
    private Label lblBienvenida;
    private Button btnProductos;
    private Button btnVentas;
    private Button btnUsuarios;
    private Button btnCerrarSesion;

    public MainView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        // Layout principal (divide en top, center, bottom)
        root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // ===== HEADER (Arriba) =====
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #2c3e50;");

        Label titulo = new Label("TIENDA EL BARRIO");
        titulo.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        lblBienvenida = new Label("Bienvenido, Usuario");
        lblBienvenida.setStyle("-fx-font-size: 14px; -fx-text-fill: #bdc3c7;");

        header.getChildren().addAll(titulo, lblBienvenida);
        root.setTop(header);

        // ===== CENTER (Menú) =====
        VBox menu = new VBox(15);
        menu.setAlignment(Pos.CENTER);
        menu.setPadding(new Insets(40));

        Label menuTitulo = new Label("Módulos del Sistema");
        menuTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Botones de módulos
        btnProductos = crearBotonMenu("📦 Productos", "#3498db");
        btnVentas = crearBotonMenu("🛒 Ventas", "#27ae60");
        btnUsuarios = crearBotonMenu("👥 Usuarios", "#9b59b6");

        menu.getChildren().addAll(menuTitulo, btnProductos, btnVentas, btnUsuarios);
        root.setCenter(menu);

        // ===== FOOTER (Abajo) =====
        HBox footer = new HBox();
        footer.setAlignment(Pos.CENTER_RIGHT);
        footer.setPadding(new Insets(20));
        footer.setStyle("-fx-background-color: #bdc3c7;");

        btnCerrarSesion = new Button("Cerrar Sesión");
        btnCerrarSesion.setStyle(
                "-fx-background-color: #e74c3c; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 20;"
        );

        footer.getChildren().add(btnCerrarSesion);
        root.setBottom(footer);
    }

    private Button crearBotonMenu(String texto, String color) {
        Button btn = new Button(texto);
        btn.setPrefWidth(250);
        btn.setPrefHeight(50);
        btn.setStyle(
                "-fx-background-color: " + color + "; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-cursor: hand;"
        );
        return btn;
    }

    public void mostrar(Stage stage, String nombreUsuario) {
        lblBienvenida.setText("Bienvenido, " + nombreUsuario);

        Scene scene = new Scene(root, 600, 500);
        stage.setTitle("Tienda Barrio - Sistema Principal");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Getters para el Controller
    public Button getBtnProductos() { return btnProductos; }
    public Button getBtnVentas() { return btnVentas; }
    public Button getBtnUsuarios() { return btnUsuarios; }
    public Button getBtnCerrarSesion() { return btnCerrarSesion; }
}