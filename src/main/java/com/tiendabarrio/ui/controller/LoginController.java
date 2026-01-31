package com.tiendabarrio.ui.controller;

import com.tiendabarrio.service.LoginService;
import com.tiendabarrio.ui.view.LoginView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class LoginController {
    private final LoginView view;
    private final Stage stage;
    private final LoginService loginService;

    public LoginController(Stage stage) {
        this.stage = stage;
        this.view = new LoginView();
        this.loginService = new LoginService();

        configurarEventos();
    }

    private void configurarEventos() {
        // Acción del botón login
        view.getLoginButton().setOnAction(e -> intentarLogin());

        // Permitir login con Enter en el campo password
        view.getPasswordField().setOnAction(e -> intentarLogin());
    }

    private void intentarLogin() {
        String email = view.getEmailField().getText().trim();
        String password = view.getPasswordField().getText();

        // Validaciones básicas
        if (email.isEmpty() || password.isEmpty()) {
            view.mostrarMensaje("Por favor complete todos los campos", true);
            return;
        }

        // Intentar autenticación
        boolean exitoso = loginService.login(email, password);

        if (exitoso) {
            view.mostrarMensaje("¡Bienvenido!", false);

            // Pequeña pausa para que se vea el mensaje verde antes de cambiar ventana
            new Thread(() -> {
                try {
                    Thread.sleep(500);
                    Platform.runLater(this::abrirVentanaPrincipal);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();

        } else {
            view.mostrarMensaje("Email o contraseña incorrectos", true);
            view.getPasswordField().clear();
        }
    }

    private void abrirVentanaPrincipal() {
        // Aquí abriremos la ventana principal (la creamos después)
        // Por ahora mostramos un mensaje temporal

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Login Exitoso");
        alert.setHeaderText(null);
        alert.setContentText("Bienvenido al sistema\n\nAquí irá la ventana principal");
        alert.showAndWait();

        // Cerrar login
        stage.close();
    }

    public void mostrar() {
        view.mostrar(stage);
    }
}