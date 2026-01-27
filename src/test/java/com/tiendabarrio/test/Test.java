package com.tiendabarrio.test;

import com.tiendabarrio.config.ConnectionFactory;

import java.sql.Connection;

public class Test {

    public static void main(String[] args) {

        try (Connection conn = ConnectionFactory.getConnection()) {

            if (conn != null && !conn.isClosed()) {
                System.out.println("CONEXIÓN OK con la base de datos");
            } else {
                System.out.println("Conexión fallida");
            }

        } catch (Exception e) {
            System.out.println("ERROR de conexión");
            e.printStackTrace();
        }
    }
}
