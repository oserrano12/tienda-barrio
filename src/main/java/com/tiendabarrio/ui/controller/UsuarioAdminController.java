package com.tiendabarrio.ui.controller;

import com.tiendabarrio.model.Rol;
import com.tiendabarrio.model.Usuario;
import com.tiendabarrio.service.RolService;
import com.tiendabarrio.service.UsuarioService;
import com.tiendabarrio.service.impl.RolServiceImpl;
import com.tiendabarrio.service.impl.UsuarioServiceImpl;
import com.tiendabarrio.ui.view.UsuarioAdminView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UsuarioAdminController {

    private final UsuarioAdminView view;
    private final Stage stage;
    private final UsuarioService usuarioService;
    private final RolService rolService;
    private ObservableList<Usuario> todosLosUsuarios;
    private Usuario usuarioSeleccionado;

    public UsuarioAdminController(Stage stage) {
        this.stage = stage;
        this.view = new UsuarioAdminView();
        this.usuarioService = new UsuarioServiceImpl();
        this.rolService = new RolServiceImpl();

        configurarEventos();
        cargarUsuarios();
    }

    private void configurarEventos() {
        // Seleccionar usuario de la tabla
        view.getTablaUsuarios().getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        usuarioSeleccionado = newSelection;
                        view.mostrarPanelAcciones(true);
                        view.getBtnActualizar().setDisable(false);
                        view.getLblTituloFormulario().setText("Editar Usuario (ID: " + newSelection.getUsuarioId() + ")");
                        view.cargarUsuarioEnFormulario(newSelection);
                    } else {
                        view.mostrarPanelAcciones(false);
                        view.getBtnActualizar().setDisable(true);
                        view.getLblTituloFormulario().setText("Crear Nuevo Usuario");
                        usuarioSeleccionado = null;
                    }
                }
        );

        // Buscar usuarios
        view.getBtnBuscar().setOnAction(e -> {
            String filtro = view.getTxtBuscar().getText().toLowerCase().trim();
            if (filtro.isEmpty()) {
                view.cargarUsuarios(todosLosUsuarios);
            } else {
                List<Usuario> filtrados = todosLosUsuarios.stream()
                        .filter(u -> u.getNombreUsuario().toLowerCase().contains(filtro)
                                || u.getEmailUsuario().toLowerCase().contains(filtro))
                        .collect(Collectors.toList());
                view.cargarUsuarios(FXCollections.observableArrayList(filtrados));
            }
        });

        // Limpiar búsqueda
        view.getBtnLimpiar().setOnAction(e -> {
            view.getTxtBuscar().clear();
            view.cargarUsuarios(todosLosUsuarios);
            limpiarTodo();
        });

        // Limpiar formulario
        view.getBtnLimpiarFormulario().setOnAction(e -> limpiarTodo());

        // Crear nuevo usuario
        view.getBtnGuardar().setOnAction(e -> {
            if (usuarioSeleccionado != null) {
                mostrarError("Usa 'Actualizar Seleccionado' para modificar, o limpia el formulario para crear nuevo");
                return;
            }
            guardarUsuarioNuevo();
        });

        // Actualizar usuario seleccionado
        view.getBtnActualizar().setOnAction(e -> {
            if (usuarioSeleccionado == null) {
                mostrarError("Selecciona un usuario de la tabla para actualizar");
                return;
            }
            actualizarUsuarioExistente();
        });

        // Activar/Desactivar usuario
        view.getBtnActivarDesactivar().setOnAction(e -> {
            if (usuarioSeleccionado == null) return;

            try {
                if (usuarioSeleccionado.isActivo()) {
                    usuarioService.desactivarUsuario(usuarioSeleccionado.getUsuarioId());
                    mostrarMensaje("Usuario desactivado correctamente");
                } else {
                    usuarioService.activarUsuario(usuarioSeleccionado.getUsuarioId());
                    mostrarMensaje("Usuario activado correctamente");
                }
                recargarUsuarioSeleccionado();
            } catch (Exception ex) {
                mostrarError("Error: " + ex.getMessage());
            }
        });

        // Cambiar contraseña
        view.getBtnCambiarPassword().setOnAction(e -> cambiarPassword());

        // Eliminar usuario
        view.getBtnEliminar().setOnAction(e -> eliminarUsuario());

        // Volver al menú
        view.getBtnVolver().setOnAction(e -> stage.close());
    }

    private void guardarUsuarioNuevo() {
        String nombre = view.getTxtNombre().getText().trim();
        String email = view.getTxtEmail().getText().trim();
        String password = view.getTxtPassword().getText();
        String rolSeleccionado = view.getCmbRol().getValue();
        boolean activo = view.getChkActivo().isSelected();

        if (nombre.isEmpty() || email.isEmpty()) {
            mostrarError("Nombre y email son obligatorios");
            return;
        }

        if (password.isEmpty()) {
            mostrarError("La contraseña es obligatoria para nuevos usuarios");
            return;
        }

        if (rolSeleccionado == null) {
            mostrarError("Debe seleccionar un rol");
            return;
        }

        try {
            Usuario nuevo = new Usuario();
            nuevo.setNombreUsuario(nombre);
            nuevo.setEmailUsuario(email);
            nuevo.setPasswordUsuario(password);
            nuevo.setActivo(activo);

            Rol rol = rolService.buscarRolPorNombre(rolSeleccionado);
            List<Integer> roles = new ArrayList<>();
            roles.add(rol.getIdRol());

            usuarioService.registrarUsuario(nuevo, roles);
            mostrarMensaje("Usuario creado correctamente con rol " + rolSeleccionado);

            recargarLista();
            limpiarTodo();

        } catch (Exception ex) {
            mostrarError("Error al crear: " + ex.getMessage());
        }
    }

    private void actualizarUsuarioExistente() {
        String nombre = view.getTxtNombre().getText().trim();
        String email = view.getTxtEmail().getText().trim();

        if (nombre.isEmpty() || email.isEmpty()) {
            mostrarError("Nombre y email son obligatorios");
            return;
        }

        try {
            usuarioSeleccionado.setNombreUsuario(nombre);
            usuarioSeleccionado.setEmailUsuario(email);
            usuarioSeleccionado.setActivo(view.getChkActivo().isSelected());

            usuarioService.actualizarUsuario(usuarioSeleccionado);

            // Actualizar rol si cambió
            String rolSeleccionado = view.getCmbRol().getValue();
            if (rolSeleccionado != null) {
                actualizarRolUsuario(usuarioSeleccionado, rolSeleccionado);
            }

            mostrarMensaje("Usuario actualizado correctamente");
            recargarLista();

        } catch (Exception ex) {
            mostrarError("Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarUsuario() {
        if (usuarioSeleccionado == null) return;

        Alert confirmar = new Alert(Alert.AlertType.CONFIRMATION);
        confirmar.setTitle("Confirmar eliminación");
        confirmar.setHeaderText("¿Eliminar usuario " + usuarioSeleccionado.getNombreUsuario() + "?");
        confirmar.setContentText("Esta acción no se puede deshacer");

        Optional<ButtonType> resultado = confirmar.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                usuarioService.eliminar(usuarioSeleccionado.getUsuarioId());
                mostrarMensaje("Usuario eliminado correctamente");
                recargarLista();
                limpiarTodo();
            } catch (Exception ex) {
                mostrarError("Error al eliminar: " + ex.getMessage());
            }
        }
    }

    private void cambiarPassword() {
        if (usuarioSeleccionado == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Cambiar Contraseña");
        dialog.setHeaderText("Usuario: " + usuarioSeleccionado.getNombreUsuario());
        dialog.setContentText("Nueva contraseña:");

        Optional<String> resultado = dialog.showAndWait();
        resultado.ifPresent(nuevaPassword -> {
            if (nuevaPassword.trim().isEmpty()) {
                mostrarError("La contraseña no puede estar vacía");
                return;
            }
            try {
                usuarioService.cambiarPassword(usuarioSeleccionado.getUsuarioId(), nuevaPassword.trim());
                mostrarMensaje("Contraseña cambiada correctamente");
            } catch (Exception ex) {
                mostrarError("Error al cambiar contraseña: " + ex.getMessage());
            }
        });
    }

    private void actualizarRolUsuario(Usuario u, String nombreRol) {
        try {
            // Eliminar roles actuales
            // Nota: Necesitarías agregar un método en UsuarioRolDAO para esto
            // Por ahora solo mostramos mensaje
            System.out.println("Rol actualizado a: " + nombreRol);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limpiarTodo() {
        view.limpiarFormulario();
        usuarioSeleccionado = null;
        view.mostrarPanelAcciones(false);
        view.getBtnActualizar().setDisable(true);
        view.getTablaUsuarios().getSelectionModel().clearSelection();
    }

    private void cargarUsuarios() {
        try {
            List<Usuario> lista = usuarioService.listarTodos();
            todosLosUsuarios = FXCollections.observableArrayList(lista);
            view.cargarUsuarios(todosLosUsuarios);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void recargarLista() {
        cargarUsuarios();
    }

    private void recargarUsuarioSeleccionado() {
        if (usuarioSeleccionado != null) {
            Usuario actualizado = usuarioService.buscarPorId(usuarioSeleccionado.getUsuarioId());
            usuarioSeleccionado = actualizado;
            view.cargarUsuarioEnFormulario(actualizado);
            recargarLista();
        }
    }

    private void mostrarMensaje(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void mostrar() {
        view.mostrar(stage);
    }
}