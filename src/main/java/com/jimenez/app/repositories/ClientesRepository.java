package com.jimenez.app.repositories;

import com.jimenez.app.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientesRepository implements IRepository<Cliente> {

    private Connection conn; //importar de java.sql

    //constructor
    public ClientesRepository(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Cliente> listar() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM CLIENTES")) {
            while (rs.next()) {
                Cliente cliente = this.getCliente(rs);
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientes;
    }


    @Override
    public Cliente getById(Long id) throws SQLException {
        Cliente cliente = null;
        try (PreparedStatement stmt =
                     conn.prepareStatement("SELECT * FROM clientes WHERE ID_CLIENTE = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = this.getCliente(rs);
                }
            }
        }
        return cliente;
    }

    @Override
    public void guardar(Cliente cliente) throws SQLException {
        String sql;
        if (cliente.getIdCliente() != null && cliente.getIdCliente() > 0) {
            sql = "update clientes set nombre=?, rfc=?," +
                    "telefono=?, direccion=?, razon_social=?," +
                    " email=?" +
                    " where id_cliente=?";
        } else {
            sql = "insert into clientes(id_cliente, nombre," +
                    "rfc, telefono, direccion, razon_social," +
                    "email)" +
                    "values (SEQUENCE_CLIENTES.NEXTVAL,?,?,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (cliente.getIdCliente() != null && cliente.getIdCliente() > 0) {
                stmt.setString(1, cliente.getNombre());
                stmt.setString(2, cliente.getRfc());
                stmt.setString(3, cliente.getTelefono());
                stmt.setString(4, cliente.getDireccion());
                stmt.setString(5, cliente.getRazonSocial());
                stmt.setString(6, cliente.getEmail());
                stmt.setLong(7, cliente.getIdCliente());
            } else {
                stmt.setString(1, cliente.getNombre());
                stmt.setString(2, cliente.getRfc());
                stmt.setString(3, cliente.getTelefono());
                stmt.setString(4, cliente.getDireccion());
                stmt.setString(5, cliente.getRazonSocial());
                stmt.setString(6, cliente.getEmail());
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sql = "delete from clientes where id_cliente=?";
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }

//mapear/transformar un renglon/fila/registro/row esn un objeto de tipo chofer

    private Cliente getCliente(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getLong("ID_CLIENTE"));
        cliente.setNombre(rs.getString("NOMBRE"));
        cliente.setRfc(rs.getString("RFC"));
        cliente.setTelefono(rs.getString("TELEFONO"));
        cliente.setDireccion(rs.getString("DIRECCION"));
        cliente.setRazonSocial(rs.getString("RAZON_SOCIAL"));
        cliente.setEmail(rs.getString("EMAIL"));
        return cliente;
    }
}
