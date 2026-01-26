package com.tiendabarrio.service.impl;

import com.tiendabarrio.dao.RolDAO;
import com.tiendabarrio.dao.impl.RolDAOImpl;
import com.tiendabarrio.model.Rol;
import com.tiendabarrio.service.RolService;
import java.util.List;

public class RolServiceImpl implements RolService {

    private final RolDAO rolDAO;

    public RolServiceImpl() {
        this.rolDAO = new RolDAOImpl();
    }

    public RolServiceImpl(RolDAO rolDAO) {
        this.rolDAO = rolDAO;
    }

    @Override
    public void crearRol(Rol rol) {
        if (rol.getNombreRol() == null || rol.getNombreRol().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre rol obligatorio");
        }

        Rol existente = rolDAO.buscarPorNombre(rol.getNombreRol());
        if (existente != null) {
            throw new RuntimeException("Rol ya existe: " + rol.getNombreRol());
        }

        rolDAO.crear(rol);
    }

    @Override
    public Rol buscarRolPorId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        Rol rol = rolDAO.buscarPorId(id);
        if (rol == null) throw new RuntimeException("Rol no encontrado");
        return rol;
    }

    @Override
    public Rol buscarRolPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre vacío");
        }
        return rolDAO.buscarPorNombre(nombre.trim());
    }

    @Override
    public List<Rol> listarTodosRoles() {
        return rolDAO.listarTodos();
    }

    @Override
    public void actualizarRol(Rol rol) {
        if (rol.getIdRol() <= 0) throw new IllegalArgumentException("ID inválido");
        if (rol.getNombreRol() == null || rol.getNombreRol().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre rol obligatorio");
        }

        Rol existente = rolDAO.buscarPorId(rol.getIdRol());
        if (existente == null) throw new RuntimeException("Rol no encontrado");

        rolDAO.actualizar(rol);
    }

    @Override
    public void eliminarRol(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        rolDAO.eliminar(id);
    }
}