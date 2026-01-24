package com.tiendabarrio;

import com.tiendabarrio.dao.impl.*;
import com.tiendabarrio.model.*;
import java.math.BigDecimal;

public class TestIDs {
    public static void main(String[] args) {
        try {
            System.out.println("=== PRUEBA DE RECUPERACIÓN DE IDs GENERADOS ===\n");

            // 1. Crear una categoría de prueba
            System.out.println("1. Probando CategoriaDAO...");
            CategoriaDAOImpl categoriaDAO = new CategoriaDAOImpl();
            Categoria categoria = new Categoria();
            categoria.setNombreCategoria("Test Categoria");
            categoria.setDescripcionCategoria("Categoría de prueba");

            categoriaDAO.crear(categoria);
            System.out.println("   ✓ Categoría creada con ID: " + categoria.getCategoriaId());

            // 2. Crear un proveedor de prueba
            System.out.println("\n2. Probando ProveedorDAO...");
            ProveedorDAOImpl proveedorDAO = new ProveedorDAOImpl();
            Proveedor proveedor = new Proveedor();
            proveedor.setNombreProveedor("Test Proveedor");
            proveedor.setEmailProveedor("test@proveedor.com");
            proveedor.setTelefonoProveedor("123456789");
            proveedor.setDireccionProveedor("Dirección test");

            proveedorDAO.crear(proveedor);
            System.out.println("   ✓ Proveedor creado con ID: " + proveedor.getProveedorId());

            // 3. Crear un producto de prueba
            System.out.println("\n3. Probando ProductoDAO...");
            ProductoDAOImpl productoDAO = new ProductoDAOImpl();
            Producto producto = new Producto();
            producto.setNombreProducto("Producto Test");
            producto.setPrecioProducto(new BigDecimal("1000.00"));
            producto.setStockProducto(50);
            producto.setActivo(true);
            producto.setCategoriaId(categoria.getCategoriaId());
            producto.setProveedorId(proveedor.getProveedorId());

            productoDAO.crear(producto);
            System.out.println("   ✓ Producto creado con ID: " + producto.getProductoId());

            // 4. Crear un usuario de prueba
            System.out.println("\n4. Probando UsuarioDAO...");
            UsuarioDAOImpl usuarioDAO = new UsuarioDAOImpl();
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("Usuario Test");
            usuario.setEmailUsuario("test@usuario.com");
            usuario.setPasswordUsuario("password123");
            usuario.setActivo(true);

            usuarioDAO.crear(usuario);
            System.out.println("   ✓ Usuario creado con ID: " + usuario.getUsuarioId());

            // 5. Crear un rol de prueba
            System.out.println("\n5. Probando RolDAO...");
            RolDAOImpl rolDAO = new RolDAOImpl();
            Rol rol = new Rol();
            rol.setNombreRol("TEST_ROLE");
            rol.setDescripcionRol("Rol de prueba");

            rolDAO.crear(rol);
            System.out.println("   ✓ Rol creado con ID: " + rol.getIdRol());

            // 6. Crear una venta de prueba
            System.out.println("\n6. Probando VentaDAO...");
            VentaDAOImpl ventaDAO = new VentaDAOImpl();
            Venta venta = new Venta();
            venta.setFechaVenta(java.time.LocalDateTime.now());
            venta.setTotalVenta(new BigDecimal("5000.00"));
            venta.setUsuarioId(usuario.getUsuarioId());

            ventaDAO.crear(venta);
            System.out.println("   ✓ Venta creada con ID: " + venta.getVentaId());

            // 7. Crear un detalle de venta de prueba
            System.out.println("\n7. Probando DetalleVentaDAO...");
            DetalleVentaDAOImpl detalleVentaDAO = new DetalleVentaDAOImpl();
            DetalleVenta detalle = new DetalleVenta();
            detalle.setVentaId(venta.getVentaId());
            detalle.setProductoId(producto.getProductoId());
            detalle.setCantidad(2);
            detalle.setPrecioUnitario(new BigDecimal("2500.00"));

            detalleVentaDAO.crear(detalle);
            System.out.println("   ✓ DetalleVenta creado con ID: " + detalle.getDetalleId());

            System.out.println("\n" + "=".repeat(50));
            System.out.println("✅ TODAS LAS PRUEBAS COMPLETADAS EXITOSAMENTE");
            System.out.println("=".repeat(50));
            System.out.println("\nResumen de IDs generados:");
            System.out.println("  Categoría: " + categoria.getCategoriaId());
            System.out.println("  Proveedor: " + proveedor.getProveedorId());
            System.out.println("  Producto: " + producto.getProductoId());
            System.out.println("  Usuario: " + usuario.getUsuarioId());
            System.out.println("  Rol: " + rol.getIdRol());
            System.out.println("  Venta: " + venta.getVentaId());
            System.out.println("  DetalleVenta: " + detalle.getDetalleId());

            // Limpieza (opcional)
            System.out.println("\n¿Deseas limpiar los datos de prueba? (s/n)");
            // Puedes agregar lógica para eliminar si quieres

        } catch (Exception e) {
            System.err.println("\n❌ ERROR EN LAS PRUEBAS:");
            System.err.println("Mensaje: " + e.getMessage());
            System.err.println("\nCausa:");
            e.printStackTrace();
            System.err.println("\nSugerencia: Verifica que la base de datos esté corriendo");
            System.err.println("y que las tablas existan con los nombres correctos.");
        }
    }
}