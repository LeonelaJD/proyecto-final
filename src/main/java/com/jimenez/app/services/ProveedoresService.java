package com.jimenez.app.services;

import com.jimenez.app.models.Proveedor;
import com.jimenez.app.repositories.IRepository;
import com.jimenez.app.repositories.ProveedoresRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProveedoresService implements IService<Proveedor> {

    private IRepository<Proveedor> proveedorRepo;

    public ProveedoresService(Connection conn) {
        proveedorRepo = new ProveedoresRepository(conn);
    }

    @Override
    public List<Proveedor> listar() {
        try {
            return proveedorRepo.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Proveedor> getById(Long id) {
        try {
            return Optional.ofNullable(proveedorRepo.getById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void guardar(Proveedor proveedor) {
        try {
            proveedorRepo.guardar(proveedor);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            proveedorRepo.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
