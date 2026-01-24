package com.tiendabarrio.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionFactory {

    // Constructor privado para evitar instanciación
    private ConnectionFactory() {}

    public static Connection getConnection() throws SQLException {
        // Obtener conexión básica
        Connection connection = DriverManager.getConnection(
                DatabaseConfig.getUrl(),
                DatabaseConfig.getUser(),
                DatabaseConfig.getPassword()
        );

        // Configurar el esquema tienda automáticamente
        configurarEsquemaTienda(connection);

        return connection;
    }

    // Configura el esquema tienda para la conexión proporcionada
    private static void configurarEsquemaTienda(Connection connection) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // Verificar si el esquema tienda existe
            boolean esquemaExiste = verificarEsquemaTienda(stmt);

            if (esquemaExiste) {
                // Configurar search_path al esquema tienda
                stmt.execute("SET search_path TO tienda");
                System.out.println("[ConnectionFactory] Esquema configurado a: tienda");
            } else {
                System.out.println("[ConnectionFactory] ⚠️ El esquema 'tienda' no existe");
                System.out.println("[ConnectionFactory] Las tablas deben estar en 'public' o crear el esquema");
            }
        }
    }

    //Verifica si el esquema 'tienda' existe en la base de datos
    private static boolean verificarEsquemaTienda(Statement stmt) throws SQLException {
        String sql = "SELECT EXISTS (" +
                "SELECT 1 FROM information_schema.schemata " +
                "WHERE schema_name = 'tienda'" +
                ")";

        try (var rs = stmt.executeQuery(sql)) {
            return rs.next() && rs.getBoolean(1);
        }
    }

    //Metodo alternativo para obtener conexión SIN configurar esquema
    // Útil para operaciones de administración de base de datos
    public static Connection getConnectionSinEsquema() throws SQLException {
        return DriverManager.getConnection(
                DatabaseConfig.getUrl(),
                DatabaseConfig.getUser(),
                DatabaseConfig.getPassword()
        );
    }

    //Metodo para probar la conexión y configuración
    public static void testConexionCompleta() {
        System.out.println("\n=== TEST DE CONEXIÓN COMPLETA ===");

        try (Connection conn = getConnection()) {
            System.out.println("✅ Conexión establecida a: " + DatabaseConfig.getUrl());

            // Verificar esquema actual
            try (Statement stmt = conn.createStatement();
                 var rs = stmt.executeQuery("SELECT current_schema()")) {

                if (rs.next()) {
                    String esquemaActual = rs.getString(1);
                    System.out.println("📁 Esquema actual: " + esquemaActual);

                    // Listar tablas en el esquema actual
                    listarTablasEnEsquema(stmt, esquemaActual);
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error de conexión: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=".repeat(40));
    }

    //Lista las tablas en el esquema especificado
    private static void listarTablasEnEsquema(Statement stmt, String esquema) throws SQLException {
        String sql = "SELECT table_name " +
                "FROM information_schema.tables " +
                "WHERE table_schema = ? " +
                "AND table_type = 'BASE TABLE' " +
                "ORDER BY table_name";

        // Usamos PreparedStatement para evitar SQL injection
        try (var pstmt = stmt.getConnection().prepareStatement(sql)) {
            pstmt.setString(1, esquema);

            try (var rs = pstmt.executeQuery()) {
                System.out.println("\n📋 Tablas en esquema '" + esquema + "':");

                int contador = 0;
                while (rs.next()) {
                    contador++;
                    System.out.println("   " + contador + ". " + rs.getString("table_name"));
                }

                if (contador == 0) {
                    System.out.println("   ⚠️ No se encontraron tablas");
                } else {
                    System.out.println("   Total: " + contador + " tablas");
                }
            }
        }
    }
}