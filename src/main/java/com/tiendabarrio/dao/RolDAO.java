package com.tiendabarrio.dao;

// Imporamos el modelo ROL
import com.tiendabarrio.model.Rol;

// Importamos list para devolver listas de resultados
import java.util.List;

public interface RolDAO {

    // Insertar nuevo ROL en la base de datos
    void crear(Rol rol);

    // Buscar un ROL por su ID
    // Devuelve el ROL o NULL si no existe
    Rol obtenerPorId(int idRol);

    // Obtiene el ROL por su nombre, devuelve NULL si no existe
    Rol obtenerPorNombre(String nombreRol);

    // Lista todos los ROLES registrados
    List<Rol> listarTodos();

    // Actualiza nombre y descripcion de un ROL existente
    // Se identifica por id_rol
    void actualizar(Rol rol);

    // Eliminar un ROL por su id_rol
    void eliminar(Rol rol);
}
