package com.jimenez.app.repositories;

import com.jimenez.app.models.Proveedor;

import java.sql.SQLException;
import java.util.List;

public interface IRepository<T>{

    List<T> listar() throws SQLException;

    T getById(Long id) throws SQLException;

    void guardar (T t) throws SQLException;

    void eliminar(Long id) throws SQLException;
}
