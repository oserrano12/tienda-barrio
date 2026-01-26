package com.tiendabarrio;

import com.tiendabarrio.model.Usuario;
import com.tiendabarrio.service.UsuarioService;
import com.tiendabarrio.service.impl.UsuarioServiceImpl;
import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        UsuarioService usuarioService = new UsuarioServiceImpl();

        System.out.println("=== PRUEBA COMPLETA BCRYPT CON ROLES ===");

        try {
            // 1. Crear usuario
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario("Admin Test");
            usuario.setEmailUsuario("admin.test@tienda.com");
            usuario.setPasswordUsuario("admin123");
            usuario.setActivo(true);

            // 2. Registrar con roles (ID 1 = ADMIN, ID 2 = CAJERO)
            System.out.println("\n1. Registrando usuario con roles...");
            usuarioService.registrarUsuario(usuario, Arrays.asList(1, 2));
            System.out.println("✓ Usuario registrado con BCrypt y roles asignados");

            // 3. Login correcto
            System.out.println("\n2. Probando login...");
            Usuario logueado = usuarioService.login("admin.test@tienda.com", "admin123");
            System.out.println("✓ Login exitoso: " + logueado.getNombreUsuario());

            // 4. Verificar en BD
            System.out.println("\n3. Verificando en BD:");
            System.out.println("Contraseña hasheada en BD (debe empezar con $2a$12$):");
            System.out.println("SELECT email_usuario, password_usuario FROM usuario WHERE email_usuario = 'admin.test@tienda.com';");

            // 5. Verificar roles asignados
            System.out.println("\n4. Verificando roles asignados:");
            System.out.println("SELECT u.nombre_usuario, r.nombre_rol ");
            System.out.println("FROM usuario u ");
            System.out.println("JOIN usuario_rol ur ON u.usuario_id = ur.usuario_id ");
            System.out.println("JOIN rol r ON ur.id_rol = r.id_rol ");
            System.out.println("WHERE u.email_usuario = 'admin.test@tienda.com';");

            System.out.println("\n=== PRUEBA COMPLETADA CON ÉXITO ===");

        } catch (Exception e) {
            System.out.println("\n✗ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}