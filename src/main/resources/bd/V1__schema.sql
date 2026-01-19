SET search_path TO tienda;

DROP TABLE IF EXISTS detalle_venta;
DROP TABLE IF EXISTS venta;
DROP TABLE IF EXISTS producto;
DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS proveedor;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS rol;

CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion_rol TEXT
);

CREATE TABLE usuario (
    usuario_id SERIAL PRIMARY KEY,
    nombre_usuario VARCHAR(100) NOT NULL UNIQUE,
    email_usuario VARCHAR(150) NOT NULL UNIQUE,
    password_usuario VARCHAR(255) NOT NULL,
    activo BOOLEAN DEFAULT TRUE,
    fecha_creacion TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categoria (
    categoria_id SERIAL PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL UNIQUE,
    descripcion_categoria TEXT
);

CREATE TABLE proveedor (
    proveedor_id SERIAL PRIMARY KEY,
    nombre_proveedor VARCHAR(150) NOT NULL,
    telefono_proveedor VARCHAR(20),
    email_proveedor VARCHAR(150) NOT NULL UNIQUE,
    direccion_proveedor VARCHAR(150)
);

CREATE TABLE usuario_rol (
    usuario_id INT REFERENCES usuarios(usuario_id),
    id_rol INT REFERENCES rol(id_rol),
    PRIMARY KEY (usuario_id, id_rol)
);

CREATE TABLE producto (
    producto_id SERIAL PRIMARY KEY,
    nombre_producto VARCHAR(150) NOT NULL,
    precio_producto NUMERIC(12, 2) NOT NULL CONSTRAINT ck1_producto CHECK (precio_producto >= 0),
    stock_producto INT NOT NULL CONSTRAINT ck2_producto CHECK (stock_producto >= 0),
    activo BOOLEAN DEFAULT TRUE,
    categoria_id INT REFERENCES categoria(categoria_id),
    proveedor_id INT REFERENCES proveedor(proveedor_id)
);

CREATE TABLE venta (
    venta_id SERIAL PRIMARY KEY,
    fecha_venta TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_venta NUMERIC(12,2) NOT NULL CONSTRAINT ck1_venta CHECK (total_venta >= 0),
    usuario_id INT REFERENCES usuario(usuario_id)
);

CREATE TABLE detalle_venta (
    detalle_id SERIAL PRIMARY KEY,
    cantidad INT NOT NULL CONSTRAINT ck1_detalle_venta CHECK (cantidad > 0),
    precio_unitario NUMERIC(12,2) NOT NULL CONSTRAINT ck2_detalle CHECK (precio_unitario >= 0),
    venta_id INT REFERENCES venta(venta_id),
    producto_id INT REFERENCES producto(producto_id)
);