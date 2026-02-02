package com.tiendabarrio.ui.controller;

import com.tiendabarrio.model.Venta;
import com.tiendabarrio.service.VentaService;
import com.tiendabarrio.service.impl.VentaServiceImpl;
import com.tiendabarrio.ui.view.VentaView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.util.List;

public class VentaController {

    // Referencias a los objetos que necesitamos
    private final VentaView view;           // La ventana visual
    private final Stage stage;              // El "escenario" de JavaFX (la ventana del SO)
    private final VentaService ventaService; // Tu lógica de negocio (ya existía)

    // Constructor: se ejecuta cuando creamos el controller
    public VentaController(Stage stage) {
        this.stage = stage;
        this.view = new VentaView();                    // Creamos la vista
        this.ventaService = new VentaServiceImpl();     // Obtenemos el service

        configurarEventos();    // Preparamos qué pasa al hacer clic
        cargarDatos();          // Traemos ventas de la BD al iniciar
    }

    // Paso 1: Configurar qué pasa cuando el usuario interactúa
    private void configurarEventos() {

        // Botón Volver: cierra esta ventana, vuelve al menú principal
        view.getBtnVolver().setOnAction(evento -> {
            stage.close();  // Cierra solo esta ventana, el menú principal sigue abierto debajo
        });

        // Botón Nueva Venta: por ahora solo muestra mensaje (lo implementamos después)
        view.getBtnNuevaVenta().setOnAction(evento -> {
            Stage nuevaVentaStage = new Stage();
            NuevaVentaController nuevaVentaController = new NuevaVentaController(nuevaVentaStage);
            nuevaVentaController.mostrar();
        });

        // Botón Ver Detalle: muestra info de la venta seleccionada
        view.getBtnVerDetalle().setOnAction(evento -> {
            Venta ventaSeleccionada = view.getTablaVentas().getSelectionModel().getSelectedItem();

            if (ventaSeleccionada == null) {
                System.out.println("Selecciona una venta primero");
            } else {
                System.out.println("Venta seleccionada: ID=" + ventaSeleccionada.getVentaId()
                        + ", Total=" + ventaSeleccionada.getTotalVenta());
            }
        });
    }

    // Paso 2: Traer datos de la base de datos y mostrarlos
    private void cargarDatos() {
        try {
            // 1. Pedir datos al Service (tu backend)
            List<Venta> listaVentas = ventaService.listarTodasVentas();

            // 2. Convertir a ObservableList (JavaFX necesita esto para la tabla)
            ObservableList<Venta> datosObservable = FXCollections.observableArrayList(listaVentas);

            // 3. Entregar datos a la View para que los pinte
            view.cargarVentas(datosObservable);

        } catch (Exception error) {
            System.err.println("Error al cargar ventas: " + error.getMessage());
            error.printStackTrace();
        }
    }

    // Método público para mostrar la ventana (lo llama MainController)
    public void mostrar() {
        view.mostrar(stage);
    }
}