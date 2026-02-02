# Tienda El Barrio - Sistema de Gestión

Sistema de punto de venta e inventario para tiendas de barrio. Proyecto académico de aprendizaje de backend Java sin frameworks.

## 🎯 Objetivo del Proyecto

Aprender desarrollo backend desde cero, implementando cada capa manualmente sin depender de frameworks como Spring o Hibernate. Entender cómo funciona realmente la arquitectura por capas: conexión a BD, DAOs, Services y presentación.

## 🛠️ Stack Tecnológico

| Tecnología | Uso |
|------------|-----|
| **Java 21** | Lenguaje principal |
| **JavaFX 21** | Interfaz gráfica de escritorio |
| **PostgreSQL** | Base de datos relacional |
| **JDBC** | Conexión nativa a BD (sin ORM) |
| **Maven** | Gestión de dependencias |
| **BCrypt** | Hash seguro de contraseñas |
| **Git/GitHub** | Control de versiones |

## 🏗️ Arquitectura del Sistema

**📁 Estructura de paquetes:**

| Paquete | Descripción |
|---------|-------------|
| `📂 config` | Configuración de conexión a PostgreSQL |
| `📂 dao` | Data Access Objects (interfaces + implementaciones JDBC) |
| `📂 model` | Entidades mapeadas 1:1 con tablas de BD |
| `📂 service` | Lógica de negocio y validaciones |
| `📂 ui.controller` | Controladores de vistas JavaFX |
| `📂 ui.view` | Vistas de la interfaz gráfica |
| `📂 util` | Utilidades (BCrypt, Sesión de usuario) |

**Flujo de datos:**  
`View (JavaFX)` → `Controller` → `Service` → `DAO (JDBC)` → `PostgreSQL`

## 🚀 Instalación y Ejecución

### Requisitos Previos
- Java JDK 21 instalado
- PostgreSQL 14+ instalado y corriendo
- Maven configurado (o usar wrapper)
- IntelliJ IDEA (Community Edition funciona)

### 1. Clonar el Repositorio
```bash
git clone https://github.com/oserrano12/tienda-barrio.git
cd tienda-barrio
```

### 2. Configurar Base de Datos
```bash
# Crear base de datos en PostgreSQL
createdb tienda_barrio

# Ejecutar scripts de migración (en orden)
psql -d tienda_barrio -f src/main/resources/bd/V1__schema.sql
psql -d tienda_barrio -f src/main/resources/bd/V2__data_inicial.sql
```

### 3. Configurar Conexión
Crear archivo `src/main/resources/application.properties`:
```properties
db.url=jdbc:postgresql://localhost:5432/tienda_barrio
db.user=postgres
db.password=tu_contraseña
```

&gt; ⚠️ **IMPORTANTE:** Este archivo está en `.gitignore` y nunca se sube al repositorio por seguridad.

### 4. Ejecutar la Aplicación
Desde IntelliJ:
- Configurar VM Options: `--module-path "ruta/a/javafx/lib" --add-modules javafx.controls,javafx.graphics`
- Ejecutar clase `Main.java`

O desde terminal:
```bash
mvn clean javafx:run
```

## 👤 Usuarios de Prueba

| Rol | Email | Password |
|-----|-------|----------|
| **ADMIN** | admin@tienda.com | admin123 |
| **VENDEDOR** | vendedor@tienda.com | admin123 |

## 📦 Funcionalidades

### Módulo de Productos (Todos los roles)
- Listado de productos desde PostgreSQL
- Búsqueda por nombre en tiempo real
- Visualización de precios en formato COP ($4.500)

### Módulo de Ventas (Todos los roles)
- Historial de ventas realizadas
- Creación de nuevas ventas con carrito
- Cálculo automático de totales
- Reducción de stock automática (transaccional)

### Módulo de Inventario (Solo ADMIN)
- Edición de nombre y precio de productos
- Aumento/reducción de stock
- Activar/desactivar productos
- Búsqueda de productos

### Control de Acceso
- Autenticación con BCrypt
- Roles: ADMIN (acceso total) y VENDEDOR (ventas y consultas)
- Sesión de usuario persistente durante la ejecución

## 🎓 Aprendizajes Clave

Este proyecto demuestra:
- **Arquitectura por capas** sin frameworks mágicos
- **Transacciones JDBC** manuales (commit/rollback)
- **Seguridad** con hash de contraseñas
- **Patrón Singleton** para sesión de usuario
- **JavaFX** para interfaces de escritorio modernas
- **Git workflow** con ramas feature y develop

## 👨‍💻 Autor

**Oscar Serrano** - Estudiante de Ingeniería de Sistemas  
Unidades Tecnológicas de Santander (UTS) - 2026-1

Proyecto académico de aprendizaje de backend Java.

---

## 📝 Notas

- Proyecto sin fines comerciales, exclusivamente educativo
- No utiliza Spring, Hibernate, Lombok ni frameworks de persistencia
- Cada línea de código es entendible y explicada