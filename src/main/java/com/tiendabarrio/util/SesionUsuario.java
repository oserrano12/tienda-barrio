package com.tiendabarrio.util;

import com.tiendabarrio.dao.RolDAO;
import com.tiendabarrio.dao.UsuarioRolDAO;
import com.tiendabarrio.dao.impl.RolDAOImpl;
import com.tiendabarrio.dao.impl.UsuarioRolDAOImpl;
import com.tiendabarrio.model.Rol;
import com.tiendabarrio.model.Usuario;
import java.util.List;

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
        // Si no está autenticado, no tiene ningún rol
        if (!autenticado || usuarioActual == null) {
            return false;
        }

        // Consultar roles reales del usuario en la base de datos
        UsuarioRolDAO usuarioRolDAO = new UsuarioRolDAOImpl();
        RolDAO rolDAO = new RolDAOImpl();

        // Obtener IDs de roles asignados al usuario
        List<Integer> rolesIds = usuarioRolDAO.obtenerRolesPorUsuario(usuarioActual.getUsuarioId());

        // Verificar si alguno coincide con el nombre buscado
        for (Integer rolId : rolesIds) {
            Rol rol = rolDAO.buscarPorId(rolId);
            if (rol != null && rol.getNombreRol().equalsIgnoreCase(nombreRol)) {
                return true;
            }
        }

        return false;
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