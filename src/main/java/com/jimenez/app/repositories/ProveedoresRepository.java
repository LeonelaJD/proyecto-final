package com.jimenez.app.repositories;

import com.jimenez.app.models.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProveedoresRepository implements IRepository<Proveedor> {

    private Connection conn; //importar de java.sql

    //constructor
    public ProveedoresRepository(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Proveedor> listar() throws SQLException {
        List<Proveedor> proveedores = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PROVEEDORES")) {
            while (rs.next()) {
                Proveedor a = this.getProveedor(rs);
                proveedores.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proveedores;
    }


    @Override
    public Proveedor getById(Long id) throws SQLException {
        Proveedor proveedor = null;
        try (PreparedStatement stmt =
                     conn.prepareStatement("SELECT * FROM proveedores WHERE ID_PROVEEDOR = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    proveedor = this.getProveedor(rs);
                }
            }
        }
        return proveedor;
    }

    @Override
    public void guardar(Proveedor proveedor) throws SQLException {
        String sql;
        if (proveedor.getIdProveedor() != null && proveedor.getIdProveedor() > 0) {
            sql = "update proveedores set nombre=?, rfc=?," +
                    "descripcion=?, razon_social=?," +
                    "direccion=?, email=?, telefono=?" +
                    " where id_proveedor=?";
        } else {
            sql = "insert into proveedores(nombre," +
                    "rfc, descripcion, razon_social," +
                    "direccion, email, telefono)" +
                    "values (SEQUENCE_PROVEEDORES.NEXTVAL?,?,?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (proveedor.getIdProveedor() != null && proveedor.getIdProveedor() > 0) {
                stmt.setString(1, proveedor.getNombre());
                stmt.setString(2, proveedor.getRfc());
                stmt.setString(3, proveedor.getDescripcion());
                stmt.setString(4, proveedor.getRazonSocial());
                stmt.setString(5, proveedor.getDireccion());
                stmt.setString(6, proveedor.getEmail());
                stmt.setString(7, proveedor.getTelefono());
                stmt.setLong(8, proveedor.getIdProveedor());
            } else {
                stmt.setString(1, proveedor.getNombre());
                stmt.setString(2, proveedor.getRfc());
                stmt.setString(3, proveedor.getDescripcion());
                stmt.setString(4, proveedor.getRazonSocial());
                stmt.setString(5, proveedor.getDireccion());
                stmt.setString(6, proveedor.getEmail());
                stmt.setString(7, proveedor.getTelefono());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from proveedores where id_proveedor=?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }

//mapear/transformar un renglon/fila/registro/row esn un objeto de tipo chofer

    private Proveedor getProveedor(ResultSet rs) throws SQLException {
        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(rs.getLong("ID_PROVEEDOR"));
        proveedor.setNombre(rs.getString("NOMBRE"));
        proveedor.setRfc(rs.getString("RFC"));
        proveedor.setDescripcion(rs.getString("DESCRIPCION"));
        proveedor.setRazonSocial(rs.getString("RAZON_SOCIAL"));
        proveedor.setDireccion(rs.getString("DIRECCION"));
        proveedor.setEmail(rs.getString("EMAIL"));
        proveedor.setTelefono(rs.getString("TELEFONO"));
        return proveedor;
    }
}