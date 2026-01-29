SET search_path TO tienda;

-- =====================================================
-- ROLES DEL SISTEMA
-- =====================================================
INSERT INTO rol (nombre_rol, descripcion_rol)
VALUES
    ('ADMIN', 'Administrador del sistema con todos los privilegios'),
    ('VENDEDOR', 'Usuario encargado de las ventas y atencion al cliente'),
    ('CAJERO', 'Empleado de caja'),
    ('INVENTARIO', 'Encargado de inventario');

-- =====================================================
-- USUARIO ADMINISTRADOR (Password: admin123)
-- Hash generado con BCrypt (12 rounds)
-- =====================================================
INSERT INTO usuario (nombre_usuario, email_usuario, password_usuario, activo)
VALUES
    ('admin', 'admin@tienda.com', '$2a$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LewKyNiAYMyzJ/I0K', true);

-- =====================================================
-- ASIGNACION ROL ADMIN
-- =====================================================
INSERT INTO usuario_rol (usuario_id, id_rol)
VALUES (
    (SELECT usuario_id FROM usuario WHERE nombre_usuario = 'admin'),
    (SELECT id_rol FROM rol WHERE nombre_rol = 'ADMIN')
);

-- =====================================================
-- CATEGORIAS BASE
-- =====================================================
INSERT INTO categoria (nombre_categoria, descripcion_categoria)
VALUES
    ('Abarrotes', 'Productos de consumo diario'),
    ('Bebidas', 'Productos liquidos para consumo'),
    ('Lacteos', 'Productos derivados de la leche'),
    ('Aseo', 'Productos de limpieza e higiene'),
    ('Snacks', 'Golosinas y productos de consumo rapido');

-- =====================================================
-- PROVEEDORES INICIALES
-- =====================================================
INSERT INTO proveedor (nombre_proveedor, telefono_proveedor, email_proveedor, direccion_proveedor)
VALUES
    ('Proveedor General', '0000000000', 'proveedor@email.com', 'Sin informacion'),
    ('Distribuidor Local', '1111111111', 'distlocal@email.com', 'Sin informacion');

-- =====================================================
-- PRODUCTOS INICIALES
-- =====================================================
INSERT INTO producto (nombre_producto, precio_producto, stock_producto, activo, categoria_id, proveedor_id)
VALUES 
    ('Gaseosa 1.5L', 4500.00, 20, TRUE, 2, 2),      -- Bebidas, Distribuidor Local
    ('Detergente 500g', 3800.00, 20, TRUE, 4, 1);   -- Aseo, Proveedor General