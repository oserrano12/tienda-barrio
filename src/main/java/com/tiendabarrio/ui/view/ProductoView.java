package com.tiendabarrio.ui.view;

import com.tiendabarrio.model.Producto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;

public class ProductoView {
    private BorderPane root;
    private TableView<Producto> tablaProductos;
    private Button btnVolver;
    private Button btnActualizar;
    private Label lblTitulo;
    private TextField txtBuscar;
    private Button btnBuscar;
    private Button btnLimpiar;

    public ProductoView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // Header
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #3498db;");

        lblTitulo = new Label("Gestión de Productos");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        header.getChildren().add(lblTitulo);

        // Campo de búsqueda
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.setPadding(new Insets(10, 0, 10, 0));

        txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar producto...");
        txtBuscar.setPrefWidth(250);

        btnBuscar = new Button("🔍 Buscar");
        btnBuscar.setStyle("-fx-padding: 5 15;");

        btnLimpiar = new Button("❌ Limpiar");
        btnLimpiar.setStyle("-fx-padding: 5 15;");

        searchBox.getChildren().addAll(txtBuscar, btnBuscar, btnLimpiar);
        header.getChildren().add(searchBox);

        root.setTop(header);

        // Tabla de productos
        tablaProductos = new TableView<>();
        tablaProductos.setStyle("-fx-font-size: 12px;");

        // Columnas
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

        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().add(tablaProductos);
        root.setCenter(centerBox);

        // Botones inferiores
        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setStyle("-fx-background-color: #bdc3c7;");

        btnActualizar = new Button("🔄 Actualizar Lista");
        btnActualizar.setStyle("-fx-padding: 10 20;");

        btnVolver = new Button("⬅ Volver al Menú");
        btnVolver.setStyle("-fx-padding: 10 20;");

        footer.getChildren().addAll(btnActualizar, btnVolver);
        root.setBottom(footer);
    }

    public void mostrar(Stage stage) {
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Productos - Tienda Barrio");
        stage.setScene(scene);
        stage.show();
    }

    public void cargarProductos(ObservableList<Producto> productos) {
        tablaProductos.setItems(productos);
        lblTitulo.setText("Gestión de Productos (" + productos.size() + " registros)");
    }

    public Button getBtnVolver() { return btnVolver; }
    public Button getBtnActualizar() { return btnActualizar; }
    public TableView<Producto> getTablaProductos() { return tablaProductos; }
    public TextField getTxtBuscar() { return txtBuscar; }
    public Button getBtnBuscar() { return btnBuscar; }
    public Button getBtnLimpiar() { return btnLimpiar; }
}