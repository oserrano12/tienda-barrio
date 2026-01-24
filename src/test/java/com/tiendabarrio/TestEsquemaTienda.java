package com.tiendabarrio;

import com.tiendabarrio.config.ConnectionFactory;
import java.sql.*;

public class TestEsquemaTienda {
    public static void main(String[] args) {
        System.out.println("=== VERIFICACIÓN COMPLETA DE DATOS EN ESQUEMA TIENDA ===\n");

        String[] tablas = {
                "rol", "usuario", "categoria", "proveedor",
                "usuario_rol", "producto", "venta", "detalle_venta"
        };

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement()) {

            System.out.println("📊 CONTEO DE REGISTROS POR TABLA:\n");

            for (String tabla : tablas) {
                try {
                    String sql = "SELECT COUNT(*) as total FROM " + tabla;
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        int total = rs.getInt("total");
                        String icon = total > 0 ? "✅" : "⚠️";
                        System.out.printf("%s %-15s: %d registros%n", icon, tabla, total);

                        // Si hay datos, mostrar detalles
                        if (total > 0 && total <= 10) {
                            mostrarDetallesTabla(stmt, tabla);
                        }
                    }
                    rs.close();

                } catch (SQLException e) {
                    System.out.printf("❌ %-15s: ERROR - %s%n", tabla, e.getMessage());
                }
            }

            // Verificar relaciones importantes
            System.out.println("\n🔗 VERIFICANDO RELACIONES:");
            verificarRelaciones(stmt);

        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }

        System.out.println("\n" + "=".repeat(50));
        System.out.println("DIAGNÓSTICO COMPLETADO");
        System.out.println("=".repeat(50));
    }

    private static void mostrarDetallesTabla(Statement stmt, String tabla) throws SQLException {
        String sql = "SELECT * FROM " + tabla + " LIMIT 5";
        ResultSet rs = stmt.executeQuery(sql);
        ResultSetMetaData meta = rs.getMetaData();
        int columnCount = meta.getColumnCount();

        System.out.println("   Detalles:");
        while (rs.next()) {
            System.out.print("     • ");
            for (int i = 1; i <= columnCount; i++) {
                String colName = meta.getColumnName(i);
                Object value = rs.getObject(i);
                // Mostrar solo columnas clave o importantes
                if (colName.contains("id") || colName.contains("nombre") ||
                        colName.contains("email") || i <= 3) {
                    System.out.print(colName + "=" + value + " ");
                }
            }
            System.out.println();
        }
        rs.close();
    }

    private static void verificarRelaciones(Statement stmt) throws SQLException {
        // 1. Verificar que el usuario admin tenga rol ADMIN
        String sql = "SELECT u.nombre_usuario, r.nombre_rol " +
                "FROM usuario u " +
                "JOIN usuario_rol ur ON u.usuario_id = ur.usuario_id " +
                "JOIN rol r ON ur.id_rol = r.id_rol " +
                "WHERE u.nombre_usuario = 'admin'";

        ResultSet rs = stmt.executeQuery(sql);
        if (rs.next()) {
            System.out.println("✅ Usuario 'admin' tiene rol: " + rs.getString("nombre_rol"));
        } else {
            System.out.println("❌ Usuario 'admin' no tiene roles asignados");
        }
        rs.close();

        // 2. Verificar productos con categorías y proveedores válidos
        sql = "SELECT p.nombre_producto, c.nombre_categoria, pr.nombre_proveedor " +
                "FROM producto p " +
                "LEFT JOIN categoria c ON p.categoria_id = c.categoria_id " +
                "LEFT JOIN proveedor pr ON p.proveedor_id = pr.proveedor_id";

        rs = stmt.executeQuery(sql);
        System.out.println("\n🛒 PRODUCTOS INSERTADOS:");
        int count = 0;
        while (rs.next()) {
            count++;
            System.out.printf("   %d. %s (Categoría: %s, Proveedor: %s)%n",
                    count,
                    rs.getString("nombre_producto"),
                    rs.getString("nombre_categoria"),
                    rs.getString("nombre_proveedor")
            );
        }
        if (count == 0) {
            System.out.println("   ⚠️ No hay productos insertados");
        }
        rs.close();
    }
}