SET search_path TO tienda;

DROP TABLE IF EXISTS usuario_rol;
DROP TABLE IF EXISTS proveedor;
DROP TABLE IF EXISTS categoria;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS rol;

CREATE TABLE rol (
    id_rol SERIAL PRIMARY KEY,
    nombre_rol VARCHAR(50) NOT NULL UNIQUE,
    descripcion_rol TEXT
);

CREATE TABLE usuarios (
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