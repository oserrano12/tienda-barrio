package com.tiendabarrio;

import com.tiendabarrio.config.DatabaseConfig;

public class Main {
    public static void main(String[] args) {

        // Prueba de lectura de configuración
        System.out.println(DatabaseConfig.getUrl());
        System.out.println(DatabaseConfig.getUser());
        System.out.println(DatabaseConfig.getPassword());
    }
}
