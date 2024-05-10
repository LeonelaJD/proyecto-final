package com.jimenez.app.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IService<T> {

    List<T> listar();
    Optional<T> getById(Long id);
    void guardar(T t);
    void eliminar(Long id);

}
