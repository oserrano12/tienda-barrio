package com.tiendabarrio.model;

import java.time.LocalDateTime;

public class usuario {
    // PRIMARY KEY
    private int usuarioId;

    // Datos del usuario
    private String nombreUsuario;
    private String emailUsuario;
    private String passwordUsuario;
    private boolean activo;
    private LocalDateTime fechaCreacion;

    public usuario() {
    }

    public usuario(int usuarioId, String nombreUsuario, String emailUsuario, String passwordUsuario, boolean activo) {
        this.usuarioId = usuarioId;
        this.nombreUsuario = nombreUsuario;
        this.emailUsuario = emailUsuario;
        this.passwordUsuario = passwordUsuario;
        this.activo = activo;
        this.fechaCreacion = LocalDateTime.now();
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPasswordUsuario() {
        return passwordUsuario;
    }

    public void setPasswordUsuario(String passwordUsuario) {
        this.passwordUsuario = passwordUsuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "usuarioId=" + usuarioId +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", emailUsuario='" + emailUsuario + '\'' +
                ", activo=" + activo +
                '}';
    }
}
