package com.tiendabarrio.model;

public class UsuarioRol {

    // FOREIGN KEYS / PRIMARY KEY COMPUESTA
    private int usuarioId;
    private int rolId;

    public UsuarioRol() {
    }

    public UsuarioRol(int usuarioId, int rolId) {
        this.usuarioId = usuarioId;
        this.rolId = rolId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getRolId() {
        return rolId;
    }

    public void setRolId(int rolId) {
        this.rolId = rolId;
    }

    @Override
    public String toString() {
        return "UsuarioRol{" +
                "usuarioId=" + usuarioId +
                ", rolId=" + rolId +
                '}';
    }
}