package com.tiendabarrio.ui.view;

import com.tiendabarrio.model.Producto;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class InventarioView {
    private BorderPane root;

    // Tabla de productos
    private TableView<Producto> tablaProductos;
    private TextField txtBuscar;
    private Button btnBuscar;
    private Button btnLimpiar;

    // Panel de edición (deshabilitado hasta seleccionar)
    private GridPane panelEdicion;
    private Label lblId;
    private TextField txtNombre;
    private TextField txtPrecio;
    private TextField txtStock;
    private CheckBox chkActivo;
    private Label lblCategoria;
    private Label lblProveedor;

    // Botones de acción
    private Button btnGuardar;
    private Button btnAumentarStock;
    private Button btnReducirStock;
    private Button btnActivarDesactivar;
    private Button btnVolver;

    // Campo para cantidad de stock a modificar
    private Spinner<Integer> spnCantidadStock;

    public InventarioView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // ===== HEADER =====
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #9b59b6;");

        Label titulo = new Label("Gestión de Inventario");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        header.getChildren().add(titulo);
        root.setTop(header);

        // ===== CENTER: Tabla + Panel de edición =====
        VBox center = new VBox(15);
        center.setPadding(new Insets(20));

        // Búsqueda
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar producto...");
        txtBuscar.setPrefWidth(250);
        btnBuscar = new Button("🔍 Buscar");
        btnLimpiar = new Button("❌ Limpiar");
        searchBox.getChildren().addAll(txtBuscar, btnBuscar, btnLimpiar);

        // Tabla
        tablaProductos = new TableView<>();

        TableColumn<Producto, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("productoId"));
        colId.setPrefWidth(50);

        TableColumn<Producto, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreProducto"));
        colNombre.setPrefWidth(200);

        TableColumn<Producto, BigDecimal> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioProducto"));
        colPrecio.setPrefWidth(100);

        TableColumn<Producto, Integer> colStock = new TableColumn<>("Stock");
        colStock.setCellValueFactory(new PropertyValueFactory<>("stockProducto"));
        colStock.setPrefWidth(80);

        TableColumn<Producto, Boolean> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        colActivo.setPrefWidth(60);

        tablaProductos.getColumns().addAll(colId, colNombre, colPrecio, colStock, colActivo);
        tablaProductos.setPrefHeight(200);

        // Panel de edición
        panelEdicion = new GridPane();
        panelEdicion.setHgap(10);
        panelEdicion.setVgap(10);
        panelEdicion.setPadding(new Insets(20));
        panelEdicion.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdc3c7;");
        panelEdicion.setDisable(true); // Deshabilitado hasta seleccionar producto

        lblId = new Label("ID: -");
        lblId.setStyle("-fx-font-weight: bold;");

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del producto");

        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio en pesos");

        txtStock = new TextField();
        txtStock.setPromptText("Stock actual");
        txtStock.setDisable(true); // No editable directamente, usamos botones

        chkActivo = new CheckBox("Producto activo");

        lblCategoria = new Label("Categoría ID: -");
        lblProveedor = new Label("Proveedor ID: -");

        // Controles de stock rápido
        spnCantidadStock = new Spinner<>(1, 1000, 10);
        spnCantidadStock.setPrefWidth(80);

        btnAumentarStock = new Button("⬆ Aumentar");
        btnAumentarStock.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

        btnReducirStock = new Button("⬇ Reducir");
        btnReducirStock.setStyle("-fx-background-color: #e67e22; -fx-text-fill: white;");

        btnActivarDesactivar = new Button("Activar/Desactivar");
        btnActivarDesactivar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        btnGuardar = new Button("💾 Guardar Cambios");
        btnGuardar.setStyle("-fx-background-color: #9b59b6; -fx-text-fill: white; -fx-font-weight: bold;");

        // Organizar panel de edición
        panelEdicion.add(lblId, 0, 0);
        panelEdicion.add(new Label("Nombre:"), 0, 1);
        panelEdicion.add(txtNombre, 1, 1);
        panelEdicion.add(new Label("Precio:"), 0, 2);
        panelEdicion.add(txtPrecio, 1, 2);
        panelEdicion.add(new Label("Stock actual:"), 0, 3);
        panelEdicion.add(txtStock, 1, 3);
        panelEdicion.add(new Label("Modificar stock:"), 0, 4);
        panelEdicion.add(spnCantidadStock, 1, 4);
        panelEdicion.add(btnAumentarStock, 2, 4);
        panelEdicion.add(btnReducirStock, 3, 4);
        panelEdicion.add(chkActivo, 0, 5);
        panelEdicion.add(btnActivarDesactivar, 1, 5);
        panelEdicion.add(lblCategoria, 0, 6);
        panelEdicion.add(lblProveedor, 1, 6);
        panelEdicion.add(btnGuardar, 0, 7, 2, 1);

        center.getChildren().addAll(searchBox, tablaProductos, panelEdicion);
        root.setCenter(center);

        // ===== FOOTER =====
        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setStyle("-fx-background-color: #bdc3c7;");

        btnVolver = new Button("⬅ Volver al Menú");
        btnVolver.setStyle("-fx-padding: 10 20;");

        footer.getChildren().add(btnVolver);
        root.setBottom(footer);
    }

    public void mostrar(Stage stage) {
        Scene scene = new Scene(root, 800, 700);
        stage.setTitle("Inventario - Tienda Barrio");
        stage.setScene(scene);
        stage.show();
    }

    public void cargarProductos(ObservableList<Producto> productos) {
        tablaProductos.setItems(productos);
    }

    public void mostrarProductoSeleccionado(Producto p) {
        panelEdicion.setDisable(false);
        lblId.setText("ID: " + p.getProductoId());
        txtNombre.setText(p.getNombreProducto());
        txtPrecio.setText(p.getPrecioProducto().toString());
        txtStock.setText(String.valueOf(p.getStockProducto()));
        chkActivo.setSelected(p.isActivo());
        lblCategoria.setText("Categoría ID: " + p.getCategoriaId());
        lblProveedor.setText("Proveedor ID: " + p.getProveedorId());
    }

    public void limpiarSeleccion() {
        panelEdicion.setDisable(true);
        lblId.setText("ID: -");
        txtNombre.clear();
        txtPrecio.clear();
        txtStock.clear();
        chkActivo.setSelected(false);
        lblCategoria.setText("Categoría ID: -");
        lblProveedor.setText("Proveedor ID: -");
    }

    // Getters
    public TableView<Producto> getTablaProductos() { return tablaProductos; }
    public TextField getTxtBuscar() { return txtBuscar; }
    public Button getBtnBuscar() { return btnBuscar; }
    public Button getBtnLimpiar() { return btnLimpiar; }
    public TextField getTxtNombre() { return txtNombre; }
    public TextField getTxtPrecio() { return txtPrecio; }
    public CheckBox getChkActivo() { return chkActivo; }
    public Spinner<Integer> getSpnCantidadStock() { return spnCantidadStock; }
    public Button getBtnAumentarStock() { return btnAumentarStock; }
    public Button getBtnReducirStock() { return btnReducirStock; }
    public Button getBtnActivarDesactivar() { return btnActivarDesactivar; }
    public Button getBtnGuardar() { return btnGuardar; }
    public Button getBtnVolver() { return btnVolver; }
    public Label getLblId() { return lblId; }
}