package com.jimenez.app.services;

import com.jimenez.app.models.Cliente;
import com.jimenez.app.models.Proveedor;
import com.jimenez.app.repositories.ClientesRepository;
import com.jimenez.app.repositories.IRepository;
import com.jimenez.app.repositories.ProveedoresRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ClientesService implements IService<Cliente> {

    private IRepository<Cliente> clienteRepo;

    public ClientesService(Connection conn) {
        clienteRepo = new ClientesRepository(conn);
    }

    @Override
    public List<Cliente> listar() {
        try {
            return clienteRepo.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Cliente> getById(Long id) {
        try {
            return Optional.ofNullable(clienteRepo.getById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void guardar(Cliente Cliente) {
        try {
            clienteRepo.guardar(Cliente);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            clienteRepo.eliminar(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
