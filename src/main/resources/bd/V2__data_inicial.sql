SET search_path TO tienda;

-- CREACION DE ROLES DEL SISTEMA
INSERT INTO rol (nombre_rol, descripcion_rol)
VALUES
    ('ADMIN', 'Administrador del sistema con todos los privilegios'),
    ('VENDEDOR', 'Usuario encargado de las ventas y atencion al cliente');

-- CREACION DE USUARIO ADMINISTRADOR
INSERT INTO usuario (nombre_usuario, email_usuario, password_usuario, activo)
VALUES
    ('admin', 'admin@tienda.com', 'PENDIENTE_IMPLEMENTAR_HASH', true);

-- ASIGNACION DEL ROL ADMIN AL USUARIO ADMIN
INSERT INTO usuario_rol (usuario_id, id_rol)
VALUES (
    (SELECT usuario_id FROM usuario WHERE nombre_usuario = 'admin'),
    (SELECT id_rol FROM rol WHERE nombre_rol = 'ADMIN')
);