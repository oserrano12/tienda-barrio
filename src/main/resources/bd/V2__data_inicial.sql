SET search_path TO tienda;

-- CREACION DE ROLES DEL SISTEMA

INSERT INTO rol (nombre_rol, descripcion_rol)
VALUES
    ('ADMIN', 'Administrador del sistema con todos los privilegios'),
    ('VENDEDOR', 'Usuario encargado de las ventas y atencion al cliente');

SELECT * FROM rol;