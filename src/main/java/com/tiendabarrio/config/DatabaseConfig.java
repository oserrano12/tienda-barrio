package com.tiendabarrio.config;

import java.io.InputStream;
import java.util.Properties;

/**
 * Esta clase se encarga de leer la configuración
 * de la base de datos desde application.properties
 */
public class DatabaseConfig {

    // Objeto Properties para cargar el archivo .properties
    private static final Properties properties = new Properties();

    // Bloque estático: se ejecuta UNA SOLA VEZ
    // cuando la clase es cargada por la JVM
    static {
        try {
            // Carga el archivo application.properties desde resources
            InputStream input = DatabaseConfig.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");

            // Si no se encuentra el archivo, es un error grave
            if (input == null) {
                throw new RuntimeException("No se encontró application.properties");
            }

            // Carga las propiedades en memoria
            properties.load(input);

        } catch (Exception e) {
            // Si algo falla, detenemos la aplicación
            throw new RuntimeException("Error cargando configuración de BD", e);
        }
    }

    // Devuelve la URL de la BD
    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    // Devuelve el usuario
    public static String getUser() {
        return properties.getProperty("db.user");
    }

    // Devuelve la contraseña (leída desde la variable de entorno)
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
}