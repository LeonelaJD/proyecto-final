package com.jimenez.app.services;

import com.jimenez.app.models.Pedido;
import com.jimenez.app.repositories.IRepository;
import com.jimenez.app.repositories.PedidosRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class PedidosService implements IService<Pedido> {

    private IRepository<Pedido> pedidoRepo;

    public PedidosService(Connection conn) {
        pedidoRepo = new PedidosRepository(conn);
    }

    @Override
    public List<Pedido> listar() {
        try {
            return pedidoRepo.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Pedido> getById(Long id) {
        try {
            return Optional.ofNullable(pedidoRepo.getById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void guardar(Pedido pedido) {
        try {
            pedidoRepo.guardar(pedido);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try {
            pedidoRepo.eliminar(id);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
