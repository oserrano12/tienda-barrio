package com.tiendabarrio.ui.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView {
    private VBox root;
    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Label mensajeLabel;

    public LoginView() {
        crearInterfaz();
    }

    private void crearInterfaz() {
        // Contenedor principal
        root = new VBox(15); // 15px de espacio entre elementos
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #f5f5f5;");

        // Título
        Label titulo = new Label("Tienda Barrio");
        titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Subtítulo
        Label subtitulo = new Label("Iniciar Sesión");
        subtitulo.setStyle("-fx-font-size: 16px; -fx-text-fill: #7f8c8d;");

        // Campo Email
        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        emailField.setPromptText("admin@tienda.com");
        emailField.setMaxWidth(250);

        // Campo Password
        Label passLabel = new Label("Contraseña:");
        passwordField = new PasswordField();
        passwordField.setPromptText("********");
        passwordField.setMaxWidth(250);

        // Botón Login
        loginButton = new Button("Ingresar");
        loginButton.setStyle(
                "-fx-background-color: #3498db; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 10 20; " +
                        "-fx-cursor: hand;"
        );

        // Label para mensajes de error/éxito
        mensajeLabel = new Label("");
        mensajeLabel.setTextFill(Color.RED);

        // Agregar todo al contenedor
        root.getChildren().addAll(
                titulo,
                subtitulo,
                emailLabel,
                emailField,
                passLabel,
                passwordField,
                loginButton,
                mensajeLabel
        );
    }

    // Método para mostrar en una ventana
    public void mostrar(Stage stage) {
        Scene scene = new Scene(root, 400, 500);
        stage.setTitle("Login - Tienda Barrio");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Getters para que el Controller acceda a los componentes
    public TextField getEmailField() {
        return emailField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Label getMensajeLabel() {
        return mensajeLabel;
    }

    public void mostrarMensaje(String mensaje, boolean esError) {
        mensajeLabel.setText(mensaje);
        mensajeLabel.setTextFill(esError ? Color.RED : Color.GREEN);
    }
}