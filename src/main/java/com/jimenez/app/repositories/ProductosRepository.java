package com.jimenez.app.repositories;

import com.jimenez.app.models.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosRepository implements IRepository<Producto> {

    private Connection conn; //importar de java.sql

    //constructor
    public ProductosRepository(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Producto> listar() {
        List<Producto> productos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTOS")) {
            while (rs.next()) {
                Producto producto = this.getProducto(rs);
                productos.add(producto);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productos;
    }

    @Override
    public Producto getById(Long id) throws SQLException {
        Producto producto = null;
        try (PreparedStatement stmt =
                     conn.prepareStatement("SELECT * FROM productos WHERE ID_PRODUCTO = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    producto = this.getProducto(rs);
                }
            }
        }
        return producto;
    }

    @Override
    public void guardar(Producto producto) throws SQLException {
        String sql = "";
        if (producto.getIdProducto() != null && producto.getIdProducto() > 0) {
            sql = "update productos set proveedor=?, categoria=?," +
                    "nombre=?, descripcion=?, precio_unitario=?," +
                    "precio_compra=?, existencia=?" +
                    " where id_producto=?";
        } else {
            sql = "insert into productos(id_producto, proveedor," +
                    "categoria, nombre, descripcion, precio_unitario," +
                    "precio_compra, existencia)" +
                    "values (SEQUENCE_PRODUCTOS.NEXTVAL,?,?,?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (producto.getIdProducto() != null && producto.getIdProducto() > 0) {
                stmt.setLong(1, producto.getProveedor());
                stmt.setLong(2, producto.getCategoria());
                stmt.setString(3, producto.getNombre());
                stmt.setString(4, producto.getDescripcion());
                stmt.setDouble(5, producto.getPrecioUnitario());
                stmt.setDouble(6, producto.getPrecioCompra());
                stmt.setDouble(7, producto.getExistencia());
                stmt.setLong(8, producto.getIdProducto());
            } else {
                stmt.setLong(1, producto.getProveedor());
                stmt.setLong(2, producto.getCategoria());
                stmt.setString(3, producto.getNombre());
                stmt.setString(4, producto.getDescripcion());
                stmt.setDouble(5, producto.getPrecioUnitario());
                stmt.setDouble(6, producto.getPrecioCompra());
                stmt.setDouble(7, producto.getExistencia());
            }
            stmt.executeUpdate();

        }

    }


    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from productos where id_producto=?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }

    private Producto getProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(rs.getLong("ID_PRODUCTO"));
        producto.setProveedor(rs.getLong("PROVEEDOR"));
        producto.setCategoria(rs.getLong("CATEGORIA"));
        producto.setNombre(rs.getString("NOMBRE"));
        producto.setDescripcion(rs.getString("DESCRIPCION"));
        producto.setPrecioUnitario(rs.getDouble("PRECIO_UNITARIO"));
        producto.setPrecioCompra(rs.getDouble("PRECIO_COMPRA"));
        producto.setExistencia(rs.getDouble("EXISTENCIA"));
        return producto;
    }
}
