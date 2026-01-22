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

    @Override
    public void crearRol(Rol rol) {
        if (rol == null) {
            throw new IllegalArgumentException("El rol no puede ser null");
        }
        rolDAO.crear(rol);
    }

    @Override
    public Rol obtenerRolPorId(int id) {
        return rolDAO.obtenerPorId(id);
    }

    @Override
    public Rol obtenerRolPorNombre(String nombre) {
        return rolDAO.obtenerPorNombre(nombre);
    }

    @Override
    public List<Rol> listarRoles() {
        return rolDAO.listarTodos();
    }

    @Override
    public void actualizarRol(Rol rol) {
        if (rol == null || rol.getIdRol() <= 0) {
            throw new IllegalArgumentException("Rol inválido");
        }
        rolDAO.actualizar(rol);
    }

    @Override
    public void eliminarRol(int id) {
        rolDAO.eliminar(id);
    }
}