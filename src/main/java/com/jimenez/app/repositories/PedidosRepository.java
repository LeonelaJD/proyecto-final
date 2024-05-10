package com.jimenez.app.repositories;

import com.jimenez.app.models.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PedidosRepository implements IRepository<Pedido> {

    private Connection conn;

    public PedidosRepository(Connection conn) {
        this.conn = conn;
    }


    @Override
    public List<Pedido> listar() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM PRODUCTOS_PEDIDOS")) {
            while (rs.next()) {
                Pedido a = this.getPedido(rs);
                pedidos.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pedidos;
    }


    @Override
    public Pedido getById(Long id) throws SQLException {
        Pedido pedido = null;
        try (PreparedStatement stmt =
                     conn.prepareStatement("SELECT * FROM PRODUCTOS_PEDIDOS WHERE ID_PRODUCTOS_PEDIDOS = ?")) {
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pedido = this.getPedido(rs);
                }
            }
        }
        return pedido;
    }

    @Override
    public void guardar(Pedido pedido) throws SQLException {
        String sql;
        if (pedido.getIdPedido() != null && pedido.getIdPedido() > 0) {
            sql = "update PRODUCTOS_PEDIDOS set cliente=?," +
                    "producto=?, cantidad=?, fecha=?" +
                    " where ID_PRODUCTOS_PEDIDOS=?";
        } else {
            sql = "insert into PRODUCTOS_PEDIDOS(ID_PRODUCTOS_PEDIDOS, cliente," +
                    "producto, cantidad, fecha)" +
                    "values (SEQUENCE_PEDIDOS.NEXTVAL,?,?,?,?)";
        }
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            if (pedido.getIdPedido() != null && pedido.getIdPedido() > 0) {
                stmt.setLong(1, pedido.getCliente());
                stmt.setDouble(2, pedido.getIdProducto());
                stmt.setDouble(3, pedido.getCantidad());
                stmt.setDate(4, Date.valueOf(pedido.getFecha()));
                stmt.setLong(5, pedido.getIdPedido());
            } else {
                stmt.setLong(1, pedido.getCliente());
                stmt.setDouble(2, pedido.getIdProducto());
                stmt.setDouble(3, pedido.getCantidad());
                stmt.setDate(4, Date.valueOf(pedido.getFecha()));
            }
            stmt.executeUpdate();
        }
    }

    @Override
    public void eliminar(Long id) throws SQLException {
        String sqlDeleteFacturas = "delete from productos_pedidos where factura=?";
        String sql = "delete from pedidos where ID_PRODUCTOS_PEDIDOS=?";
        try(PreparedStatement stmt = conn.prepareStatement(sqlDeleteFacturas)){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setLong(1,id);
            stmt.executeUpdate();
        }
    }

    private Pedido getPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getLong("ID_PRODUCTOS_PEDIDOS"));
        pedido.setCliente(Long.valueOf(rs.getString("CLIENTE")));
        pedido.setIdProducto(Long.valueOf(rs.getString("PRODUCTO")));
        pedido.setCantidad(Long.valueOf(rs.getString("CANTIDAD")));
        pedido.setFecha(rs.getDate("FECHA").toLocalDate());
        return pedido;
    }
}