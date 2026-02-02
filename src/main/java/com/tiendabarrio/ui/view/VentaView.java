package com.tiendabarrio.ui.view;

import com.tiendabarrio.model.Venta;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VentaView {
    private BorderPane root;
    private TableView<Venta> tablaVentas;
    private Button btnVolver;
    private Button btnNuevaVenta;
    private Button btnVerDetalle;
    private Label lblTitulo;

    public VentaView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // Header
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #27ae60;");

        lblTitulo = new Label("Historial de Ventas");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        header.getChildren().add(lblTitulo);
        root.setTop(header);

        // Tabla de ventas
        tablaVentas = new TableView<>();
        tablaVentas.setStyle("-fx-font-size: 12px;");

        TableColumn<Venta, Integer> colId = new TableColumn<>("ID Venta");
        colId.setCellValueFactory(new PropertyValueFactory<>("ventaId"));
        colId.setPrefWidth(80);

        TableColumn<Venta, LocalDateTime> colFecha = new TableColumn<>("Fecha");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaVenta"));
        colFecha.setPrefWidth(150);

        TableColumn<Venta, BigDecimal> colTotal = new TableColumn<>("Total");
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalVenta"));
        colTotal.setPrefWidth(100);

        TableColumn<Venta, Integer> colUsuario = new TableColumn<>("Vendedor ID");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuarioId"));
        colUsuario.setPrefWidth(100);

        tablaVentas.getColumns().addAll(colId, colFecha, colTotal, colUsuario);

        VBox centerBox = new VBox(10);
        centerBox.setPadding(new Insets(20));
        centerBox.getChildren().add(tablaVentas);
        root.setCenter(centerBox);

        // Botones inferiores
        HBox footer = new HBox(10);
        footer.setAlignment(Pos.CENTER);
        footer.setPadding(new Insets(20));
        footer.setStyle("-fx-background-color: #bdc3c7;");

        btnNuevaVenta = new Button("➕ Nueva Venta");
        btnNuevaVenta.setStyle("-fx-padding: 10 20; -fx-background-color: #27ae60; -fx-text-fill: white;");

        btnVerDetalle = new Button("👁 Ver Detalle");
        btnVerDetalle.setStyle("-fx-padding: 10 20;");

        btnVolver = new Button("⬅ Volver al Menú");
        btnVolver.setStyle("-fx-padding: 10 20;");

        footer.getChildren().addAll(btnNuevaVenta, btnVerDetalle, btnVolver);
        root.setBottom(footer);
    }

    public void mostrar(Stage stage) {
        Scene scene = new Scene(root, 700, 500);
        stage.setTitle("Ventas - Tienda Barrio");
        stage.setScene(scene);
        stage.show();
    }

    public void cargarVentas(ObservableList<Venta> ventas) {
        tablaVentas.setItems(ventas);
        lblTitulo.setText("Historial de Ventas (" + ventas.size() + " registros)");
    }

    public Button getBtnVolver() { return btnVolver; }
    public Button getBtnNuevaVenta() { return btnNuevaVenta; }
    public Button getBtnVerDetalle() { return btnVerDetalle; }
    public TableView<Venta> getTablaVentas() { return tablaVentas; }
}