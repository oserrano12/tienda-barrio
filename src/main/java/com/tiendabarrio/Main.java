package com.tiendabarrio;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Configurar la ventana principal
        primaryStage.setTitle("Tienda Barrio - Sistema de Gestión");

        // Layout básico (caja vertical)
        VBox root = new VBox(10); // 10 es el espacio entre elementos
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        // Un texto de prueba
        Label label = new Label("Sistema cargado correctamente");
        label.setStyle("-fx-font-size: 18px;");

        // Agregar elementos al layout
        root.getChildren().add(label);

        // Crear escena (contenido de la ventana)
        Scene scene = new Scene(root, 400, 300); // ancho x alto

        // Asignar escena a la ventana y mostrar
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args); // Lanza JavaFX
    }
}