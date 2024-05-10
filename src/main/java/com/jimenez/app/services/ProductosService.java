package com.jimenez.app.services;

import com.jimenez.app.models.Producto;
import com.jimenez.app.repositories.IRepository;
import com.jimenez.app.repositories.ProductosRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ProductosService implements IService<Producto> {

    private IRepository<Producto> productosRepo;

    public ProductosService(Connection conn) {
        productosRepo = new ProductosRepository(conn);
    }

    @Override
    public List<Producto> listar() {
        try {
            return productosRepo.listar();
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public Optional<Producto> getById(Long id) {
        try {
            return Optional.ofNullable(productosRepo.getById(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void guardar(Producto producto) {
        try{
            productosRepo.guardar(producto);
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    @Override
    public void eliminar(Long id) {
        try{
            productosRepo.eliminar(id);
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }
}

