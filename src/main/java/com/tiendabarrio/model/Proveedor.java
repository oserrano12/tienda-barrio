package com.tiendabarrio.model;

public class Proveedor {
    // PRIMARY KEY
    private int proveedorId;

    // Datos del proveedor
    private String nombreProveedor;
    private String telefonoProveedor;
    private String emailProveedor;
    private String direccionProveedor;

    public Proveedor() {
    }

    public Proveedor(int proveedorId,  String nombreProveedor,  String telefonoProveedor, String emailProveedor, String direccionProveedor) {
        this.proveedorId = proveedorId;
        this.nombreProveedor = nombreProveedor;
        this.telefonoProveedor = telefonoProveedor;
        this.emailProveedor = emailProveedor;
        this.direccionProveedor = direccionProveedor;
    }

    public int getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(int proveedorId) {
        this.proveedorId = proveedorId;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getEmailProveedor() {
        return emailProveedor;
    }

    public void setEmailProveedor(String emailProveedor) {
        this.emailProveedor = emailProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    @Override
    public String toString() {
        return "Proveedor{" +
                "proveedorId=" + proveedorId +
                ", nombreProveedor='" + nombreProveedor + '\'' +
                ", emailProveedor='" + emailProveedor + '\'' +
                '}';
    }
}
