package com.tiendabarrio.ui.view;

import com.tiendabarrio.model.Usuario;
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

import java.time.LocalDateTime;

public class UsuarioAdminView {
    private BorderPane root;

    // Tabla de usuarios
    private TableView<Usuario> tablaUsuarios;
    private TextField txtBuscar;
    private Button btnBuscar;
    private Button btnLimpiar;

    // Panel de creación/edición
    private GridPane panelFormulario;
    private TextField txtNombre;
    private TextField txtEmail;
    private PasswordField txtPassword;
    private CheckBox chkActivo;
    private ComboBox<String> cmbRol;
    private Button btnGuardar;
    private Button btnActualizar;
    private Button btnLimpiarFormulario;
    private Label lblTituloFormulario;

    // Panel de acciones sobre usuario seleccionado
    private HBox panelAcciones;
    private Button btnActivarDesactivar;
    private Button btnCambiarPassword;
    private Button btnEliminar;
    private Button btnVolver;

    public UsuarioAdminView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: #ecf0f1;");

        // ===== HEADER =====
        VBox header = new VBox(10);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20));
        header.setStyle("-fx-background-color: #e74c3c;");

        Label titulo = new Label("Gestión de Usuarios");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");
        header.getChildren().add(titulo);
        root.setTop(header);

        // ===== CENTER: Tabla + Formulario =====
        VBox center = new VBox(15);
        center.setPadding(new Insets(20));

        // Búsqueda
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER_LEFT);
        txtBuscar = new TextField();
        txtBuscar.setPromptText("Buscar por nombre o email...");
        txtBuscar.setPrefWidth(300);
        btnBuscar = new Button("🔍 Buscar");
        btnLimpiar = new Button("❌ Limpiar");
        searchBox.getChildren().addAll(txtBuscar, btnBuscar, btnLimpiar);

        // Tabla de usuarios
        tablaUsuarios = new TableView<>();

        TableColumn<Usuario, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("usuarioId"));
        colId.setPrefWidth(50);

        TableColumn<Usuario, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreUsuario"));
        colNombre.setPrefWidth(150);

        TableColumn<Usuario, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("emailUsuario"));
        colEmail.setPrefWidth(200);

        TableColumn<Usuario, Boolean> colActivo = new TableColumn<>("Activo");
        colActivo.setCellValueFactory(new PropertyValueFactory<>("activo"));
        colActivo.setPrefWidth(60);

        TableColumn<Usuario, LocalDateTime> colFecha = new TableColumn<>("Fecha Creación");
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCreacion"));
        colFecha.setPrefWidth(150);

        tablaUsuarios.getColumns().addAll(colId, colNombre, colEmail, colActivo, colFecha);
        tablaUsuarios.setPrefHeight(200);

        // Panel de acciones sobre usuario seleccionado
        panelAcciones = new HBox(10);
        panelAcciones.setAlignment(Pos.CENTER);
        panelAcciones.setPadding(new Insets(10));
        panelAcciones.setStyle("-fx-background-color: #f39c12;");

        btnActivarDesactivar = new Button("Activar/Desactivar");
        btnCambiarPassword = new Button("Cambiar Contraseña");
        btnEliminar = new Button("🗑 Eliminar");
        btnEliminar.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white;");

        panelAcciones.getChildren().addAll(
                new Label("Acciones sobre seleccionado:"),
                btnActivarDesactivar,
                btnCambiarPassword,
                btnEliminar
        );
        panelAcciones.setVisible(false);

        // Formulario de creación/edición
        panelFormulario = new GridPane();
        panelFormulario.setHgap(10);
        panelFormulario.setVgap(10);
        panelFormulario.setPadding(new Insets(20));
        panelFormulario.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bdc3c7;");

        lblTituloFormulario = new Label("Crear Nuevo Usuario");
        lblTituloFormulario.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre de usuario");

        txtEmail = new TextField();
        txtEmail.setPromptText("email@ejemplo.com");

        txtPassword = new PasswordField();
        txtPassword.setPromptText("Contraseña");

        chkActivo = new CheckBox("Usuario activo");
        chkActivo.setSelected(true);

        cmbRol = new ComboBox<>();
        cmbRol.getItems().addAll("ADMIN", "VENDEDOR", "CAJERO", "INVENTARIO");
        cmbRol.setPromptText("Seleccionar rol");

        HBox botonesFormulario = new HBox(10);

        btnGuardar = new Button("➕ Crear Nuevo");
        btnGuardar.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-font-weight: bold;");

        btnActualizar = new Button("💾 Actualizar Seleccionado");
        btnActualizar.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
        btnActualizar.setDisable(true);

        btnLimpiarFormulario = new Button("🔄 Limpiar");

        botonesFormulario.getChildren().addAll(btnGuardar, btnActualizar, btnLimpiarFormulario);

        panelFormulario.add(lblTituloFormulario, 0, 0, 2, 1);
        panelFormulario.add(new Label("Nombre:"), 0, 1);
        panelFormulario.add(txtNombre, 1, 1);
        panelFormulario.add(new Label("Email:"), 0, 2);
        panelFormulario.add(txtEmail, 1, 2);
        panelFormulario.add(new Label("Password:"), 0, 3);
        panelFormulario.add(txtPassword, 1, 3);
        panelFormulario.add(new Label("Rol:"), 0, 4);
        panelFormulario.add(cmbRol, 1, 4);
        panelFormulario.add(chkActivo, 0, 5);
        panelFormulario.add(botonesFormulario, 0, 6, 2, 1);

        center.getChildren().addAll(searchBox, tablaUsuarios, panelAcciones, panelFormulario);
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
        Scene scene = new Scene(root, 800, 750);
        stage.setTitle("Gestión de Usuarios - Tienda Barrio");
        stage.setScene(scene);
        stage.show();
    }

    public void cargarUsuarios(ObservableList<Usuario> usuarios) {
        tablaUsuarios.setItems(usuarios);
    }

    public void mostrarPanelAcciones(boolean mostrar) {
        panelAcciones.setVisible(mostrar);
    }

    public void limpiarFormulario() {
        txtNombre.clear();
        txtEmail.clear();
        txtPassword.clear();
        chkActivo.setSelected(true);
        cmbRol.setValue(null);
        lblTituloFormulario.setText("Crear Nuevo Usuario");
    }

    public void cargarUsuarioEnFormulario(Usuario u) {
        txtNombre.setText(u.getNombreUsuario());
        txtEmail.setText(u.getEmailUsuario());
        txtPassword.clear();
        chkActivo.setSelected(u.isActivo());
    }

    // Getters
    public TableView<Usuario> getTablaUsuarios() { return tablaUsuarios; }
    public TextField getTxtBuscar() { return txtBuscar; }
    public Button getBtnBuscar() { return btnBuscar; }
    public Button getBtnLimpiar() { return btnLimpiar; }
    public TextField getTxtNombre() { return txtNombre; }
    public TextField getTxtEmail() { return txtEmail; }
    public PasswordField getTxtPassword() { return txtPassword; }
    public CheckBox getChkActivo() { return chkActivo; }
    public ComboBox<String> getCmbRol() { return cmbRol; }
    public Button getBtnGuardar() { return btnGuardar; }
    public Button getBtnActualizar() { return btnActualizar; }
    public Button getBtnLimpiarFormulario() { return btnLimpiarFormulario; }
    public Button getBtnActivarDesactivar() { return btnActivarDesactivar; }
    public Button getBtnCambiarPassword() { return btnCambiarPassword; }
    public Button getBtnEliminar() { return btnEliminar; }
    public Button getBtnVolver() { return btnVolver; }
    public Label getLblTituloFormulario() { return lblTituloFormulario; }
}