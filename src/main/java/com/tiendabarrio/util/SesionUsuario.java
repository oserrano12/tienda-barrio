package com.tiendabarrio.util;

import com.tiendabarrio.model.Usuario;

public class SesionUsuario {
    private static SesionUsuario instancia;

    // Datos de usuario en sesion
    private Usuario usuarioActual;

    // Estado de autenticacion
    private boolean autenticado;

    // Constructor privado - solo esta clase puede crear instancias
    private SesionUsuario() {
        this.autenticado = false;
        this.usuarioActual = null;
    }

    // Metodo global para obtener la unica instancia
    public static SesionUsuario getInstancia() {
        // Crea la instancia solo la primera vez
        if (instancia == null) {
            instancia = new SesionUsuario();
        }
        return instancia;
    }

    // Establece el usuario como logueado
    public void iniciarSesion(Usuario usuario) {
        usuarioActual = usuario;
        this.autenticado = true;
        System.out.println("Sesion iniciada" + usuario.getNombreUsuario());
    }

    // Limpia los datos de sesion
    public void cerrarSesion() {
        if (this.usuarioActual != null) {
            System.out.println("Sesion cerrada" + usuarioActual.getNombreUsuario());
        }
        this.usuarioActual = null;
        this.autenticado = false;
    }

    // Obtine el usuario actual (puede ser null)
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    // Verifica si hay alguien logueado
    public boolean isAutenticado() {
        return autenticado;
    }

    // Verifica si el usuario tiene un rol especifico
    public boolean tieneRol(String nombreRol) {
        // Si no esta autenticado no tiene ningun rol
        if (!autenticado || usuarioActual == null) {
            return false;
        }
        return true;
    }

    // Muestra el estado de la sesion (para debugging)
    public void imprimirEstado() {
        System.out.println("Estado de Sesion");
        System.out.println("Autenticado: " + autenticado);
        if (usuarioActual != null) {
            System.out.println("Usuario: " + usuarioActual.getNombreUsuario());
            System.out.println("Email: " + usuarioActual.getEmailUsuario());
        }
        else  {
            System.out.println("Usuario: No hay sesion activa");
        }
        System.out.println("=================================");
    }
}