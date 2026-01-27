package com.tiendabarrio.service;

import com.tiendabarrio.model.Usuario;
import com.tiendabarrio.service.impl.UsuarioServiceImpl;
import com.tiendabarrio.util.SesionUsuario;

public class LoginService {
    // Servicio que maneja la lógica de usuarios
    private final UsuarioServiceImpl usuarioService;

    public LoginService() {
        this.usuarioService = new UsuarioServiceImpl();
    }

    // Intenta autenticar un usuario
    public boolean login(String email, String password) {
        try {
            // Valida credenciales con el servicio de usuarios
            Usuario usuario = usuarioService.login(email, password);

            if (usuario != null) {
                // Inicia sesión en el sistema
                SesionUsuario sesion = SesionUsuario.getInstancia();
                sesion.iniciarSesion(usuario);
                return true;
            }
            return false;

        } catch (RuntimeException e) {
            // Si hay error, solo devuelve false
            System.err.println("Error en login: " + e.getMessage());
            return false;
        }
    }

    // Cierra la sesión actual
    public void logout() {
        SesionUsuario sesion = SesionUsuario.getInstancia();
        sesion.cerrarSesion();
    }

    // Verifica si hay sesión activa
    public boolean isUsuarioAutenticado() {
        return SesionUsuario.getInstancia().isAutenticado();
    }

    // Obtiene el usuario actualmente logueado
    public Usuario getUsuarioActual() {
        return SesionUsuario.getInstancia().getUsuarioActual();
    }
}