package com.tiendabarrio;

import com.tiendabarrio.ui.controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Crear y mostrar el controlador de login
        LoginController loginController = new LoginController(primaryStage);
        loginController.mostrar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}