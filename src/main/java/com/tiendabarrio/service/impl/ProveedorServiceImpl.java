package com.tiendabarrio.service.impl;

import com.tiendabarrio.dao.ProveedorDAO;
import com.tiendabarrio.dao.impl.ProveedorDAOImpl;
import com.tiendabarrio.model.Proveedor;
import com.tiendabarrio.service.ProveedorService;
import java.util.List;
import java.util.regex.Pattern;

public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorDAO proveedorDAO;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public ProveedorServiceImpl() {
        this.proveedorDAO = new ProveedorDAOImpl();
    }

    public ProveedorServiceImpl(ProveedorDAO proveedorDAO) {
        this.proveedorDAO = proveedorDAO;
    }

    @Override
    public void crearProveedor(Proveedor proveedor) {
        validarProveedor(proveedor);

        Proveedor existente = buscarProveedorPorEmail(proveedor.getEmailProveedor());
        if (existente != null) {
            throw new RuntimeException("Proveedor ya existe con ese email");
        }

        proveedorDAO.crear(proveedor);
    }

    @Override
    public Proveedor buscarProveedorPorId(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");
        Proveedor proveedor = proveedorDAO.buscarPorId(id);
        if (proveedor == null) throw new RuntimeException("Proveedor no encontrado");
        return proveedor;
    }

    @Override
    public List<Proveedor> listarTodosProveedores() {
        return proveedorDAO.listarTodos();
    }

    @Override
    public void actualizarProveedor(Proveedor proveedor) {
        validarProveedor(proveedor);
        if (proveedor.getProveedorId() <= 0) throw new IllegalArgumentException("ID inválido");

        Proveedor existente = proveedorDAO.buscarPorId(proveedor.getProveedorId());
        if (existente == null) throw new RuntimeException("Proveedor no encontrado");

        proveedorDAO.actualizar(proveedor);
    }

    @Override
    public void eliminarProveedor(int id) {
        if (id <= 0) throw new IllegalArgumentException("ID inválido");

        // TODO: Verificar que no tenga productos asociados

        proveedorDAO.eliminar(id);
    }

    @Override
    public Proveedor buscarProveedorPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) throw new IllegalArgumentException("Email vacío");

        List<Proveedor> proveedores = proveedorDAO.listarTodos();
        return proveedores.stream()
                .filter(p -> p.getEmailProveedor().equalsIgnoreCase(email.trim()))
                .findFirst()
                .orElse(null);
    }

    private void validarProveedor(Proveedor proveedor) {
        if (proveedor == null) throw new IllegalArgumentException("Proveedor nulo");
        if (proveedor.getNombreProveedor() == null || proveedor.getNombreProveedor().trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre proveedor obligatorio");
        }
        if (proveedor.getEmailProveedor() == null || proveedor.getEmailProveedor().trim().isEmpty()) {
            throw new IllegalArgumentException("Email proveedor obligatorio");
        }
        if (!EMAIL_PATTERN.matcher(proveedor.getEmailProveedor()).matches()) {
            throw new IllegalArgumentException("Email no válido");
        }
    }
}