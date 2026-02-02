package com.tiendabarrio.ui.view;

import com.tiendabarrio.model.Producto;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class NuevaVentaView {
    private BorderPane root;

    // Componentes
    private ComboBox<Producto> cmbProductos;
    private Spinner<Integer> spnCantidad;
    private Button btnAgregar;
    private TableView<ItemCarrito> tablaCarrito;
    private Label lblTotal;
    private Button btnFinalizar;
    private Button btnCancelar;

    // Clase interna para el carrito
    public static class ItemCarrito {
        private Producto producto;
        private int cantidad;
        private BigDecimal subtotal;

        public ItemCarrito(Producto producto, int cantidad) {
            this.producto = producto;
            this.cantidad = cantidad;
            this.subtotal = producto.getPrecioProducto().multiply(BigDecimal.valueOf(cantidad));
        }

        public String getNombreProducto() { return producto.getNombreProducto(); }
        public int getCantidad() { return cantidad; }
        public BigDecimal getPrecioUnitario() { return producto.getPrecioProducto(); }
        public BigDecimal getSubtotal() { return subtotal; }
        public Producto getProducto() { return producto; }
    }

    public NuevaVentaView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // Header
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #e67e22;");

        Label titulo = new Label("Nueva Venta");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        header.getChildren().add(titulo);
        root.setTop(header);

        // Centro: Agregar productos + Carrito
        VBox center = new VBox(15);
        center.setPadding(new Insets(20));

        // Panel de agregar producto
        HBox panelAgregar = new HBox(10);
        panelAgregar.setAlignment(Pos.CENTER_LEFT);

        cmbProductos = new ComboBox<>();
        cmbProductos.setPromptText("Seleccionar producto...");
        cmbProductos.setPrefWidth(250);

        spnCantidad = new Spinner<>(1, 100, 1);
        spnCantidad.setPrefWidth(80);

        btnAgregar = new Button("➕ Agregar");
        btnAgregar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 5 15;");

        panelAgregar.getChildren().addAll(
                new Label("Producto:"), cmbProductos,
                new Label("Cantidad:"), spnCantidad,
                btnAgregar
        );

        // Tabla del carrito
        tablaCarrito = new TableView<>();

        TableColumn<ItemCarrito, String> colNombre = new TableColumn<>("Producto");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colNombre.setPrefWidth(200);

        TableColumn<ItemCarrito, Integer> colCant = new TableColumn<>("Cantidad");
        colCant.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colCant.setPrefWidth(80);

        TableColumn<ItemCarrito, BigDecimal> colPrecio = new TableColumn<>("Precio Unit.");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colPrecio.setPrefWidth(100);

        TableColumn<ItemCarrito, BigDecimal> colSubtotal = new TableColumn<>("Subtotal");
        colSubtotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
        colSubtotal.setPrefWidth(100);

        tablaCarrito.getColumns().addAll(colNombre, colCant, colPrecio, colSubtotal);
        tablaCarrito.setPrefHeight(250);

        // Total
        lblTotal = new Label("Total: $0");
        lblTotal.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        center.getChildren().addAll(panelAgregar, new Label("Carrito:"), tablaCarrito, lblTotal);
        root.setCenter(center);

        // Footer: Botones de acción
        HBox footer = new HBox(15);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setStyle("-fx-background-color: #bdc3c7;");

        btnFinalizar = new Button("✅ Finalizar Venta");
        btnFinalizar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 10 20;");

        btnCancelar = new Button("❌ Cancelar");
        btnCancelar.setStyle("-fx-padding: 10 20;");

        footer.getChildren().addAll(btnFinalizar, btnCancelar);
        root.setBottom(footer);
    }

    public void mostrar(Stage stage) {
        Scene scene = new Scene(root, 600, 550);
        stage.setTitle("Nueva Venta - Tienda Barrio");
        stage.setScene(scene);
        stage.show();
    }

    // Getters
    public ComboBox<Producto> getCmbProductos() { return cmbProductos; }
    public Spinner<Integer> getSpnCantidad() { return spnCantidad; }
    public Button getBtnAgregar() { return btnAgregar; }
    public TableView<ItemCarrito> getTablaCarrito() { return tablaCarrito; }
    public Label getLblTotal() { return lblTotal; }
    public Button getBtnFinalizar() { return btnFinalizar; }
    public Button getBtnCancelar() { return btnCancelar; }
}