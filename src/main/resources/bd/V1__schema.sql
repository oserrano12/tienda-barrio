SET search_path TO tienda;

-- Eliminacion de tablas si ya existen en cascada.
DROP TABLE IF EXISTS detalle_venta;
DROP TABLE IF EXISTS venta;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS proveedor;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS rol;

-- Creacion de las tablas con sus respectivas columnas, tipos de datos y restricciones.

-- Tabla rol
CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion_rol TEXT
);

-- Tabla usuario
CREATE TABLE usuario (
    usuario_id SERIAL PRIMARY KEY,
    nombre_usuario VARCHAR(100) NOT NULL UNIQUE,
    email_usuario VARCHAR(150) NOT NULL UNIQUE,
    password_usuario VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Tabla categoria
CREATE TABLE categoria (
    categoria_id SERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion_categoria TEXT
);

-- Tabla proveedor
CREATE TABLE proveedor (
    proveedor_id SERIAL PRIMARY KEY,
    nombre_proveedor VARCHAR(150) NOT NULL,
    telefono_proveedor VARCHAR(20),
    email_proveedor VARCHAR(150) NOT NULL UNIQUE,
    direccion_proveedor VARCHAR(150)
);

-- Tabla usuario_rol para relacionar usuarios con roles
CREATE TABLE usuario_rol (
    usuario_id INT NOT NULL,
    id_rol INT NOT NULL,
    PRIMARY KEY (usuario_id, id_rol),

    CONSTRAINT fk1_usuario_rol_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id) ON DELETE CASCADE,
    CONSTRAINT fk2_usuario_rol_rol
        FOREIGN KEY (id_rol) REFERENCES rol(id_rol) ON DELETE CASCADE
);

-- Tabla producto
CREATE TABLE producto (
    producto_id SERIAL PRIMARY KEY,
    nombre_producto VARCHAR(150) NOT NULL,
    precio_producto NUMERIC(12,2) NOT NULL CHECK (precio_producto >= 0),
    stock_producto INT NOT NULL CHECK (stock_producto >= 0),
    activo BOOLEAN NOT NULL DEFAULT TRUE,

    categoria_id INT NOT NULL,
    proveedor_id INT NOT NULL,
    CONSTRAINT fk1_producto FOREIGN KEY (categoria_id) REFERENCES categoria(categoria_id),
    CONSTRAINT fk2_producto FOREIGN KEY (proveedor_id) REFERENCES proveedor(proveedor_id)
);


-- Tabla venta
CREATE TABLE venta (
    venta_id SERIAL PRIMARY KEY,
    fecha_venta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_venta NUMERIC(12,2) NOT NULL CHECK (total_venta >= 0),
    
    usuario_id INT NOT NULL,
    CONSTRAINT fk1_venta FOREIGN KEY (usuario_id) REFERENCES usuario(usuario_id)
);


-- Tabla detalle_venta
CREATE TABLE detalle_venta (
    detalle_id SERIAL PRIMARY KEY,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario NUMERIC(12,2) NOT NULL CHECK (precio_unitario >= 0),

    venta_id INT NOT NULL,
    producto_id INT NOT NULL,
    CONSTRAINT fk_detalle_venta FOREIGN KEY (venta_id) REFERENCES venta(venta_id) ON DELETE CASCADE,
    CONSTRAINT fk_detalle_producto FOREIGN KEY (producto_id) REFERENCES producto(producto_id)
);