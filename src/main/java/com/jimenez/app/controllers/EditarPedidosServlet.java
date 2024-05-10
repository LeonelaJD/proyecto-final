package com.jimenez.app.controllers;

import com.jimenez.app.models.Pedido;
import com.jimenez.app.models.Proveedor;
import com.jimenez.app.services.IService;
import com.jimenez.app.services.PedidosService;
import com.jimenez.app.services.ProveedoresService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/pedidos/editar")
public class EditarPedidosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Pedido> service = new PedidosService(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));//333
        } catch (NumberFormatException e) {
            id = 0L;
        }
        Pedido pedido;
        if (id > 0) {
            Optional<Pedido> o = service.getById(id);
            if (o.isPresent()) {
                pedido = o.get();
                req.setAttribute("pedido", pedido);
                getServletContext().getRequestDispatcher("/editarPedidos.jsp")
                        .forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No existe el pedido en la base de datos!");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Error, el id es null, se debe enviar como parametro en la url!");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Pedido> service = new PedidosService(conn);

        Long idPedido = Long.parseLong(req.getParameter("id"));
        Long cliente = Long.parseLong(req.getParameter("cliente"));
        Long producto = Long.parseLong(req.getParameter("producto"));
        Long cantidad = Long.parseLong(req.getParameter("cantidad"));
        LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));

        Map<String, String> errores = new HashMap<>();
        if (idPedido == null) {
            errores.put("idPedido", "el idPedido es requerido");
        }
        if (cliente == null) {
            errores.put("cliente", "el cliente es requerido");
        }
        if (producto == null) {
            errores.put("producto", "el producto es requerido");
        }
        if (cantidad == null) {
            errores.put("cantidad", "la cantidad es requerida");
        }
        if (fecha == null) {
            errores.put("email", "el email es requerido");
        }

        Pedido pedido = new Pedido();
        pedido.setIdPedido(idPedido);
        pedido.setCliente(cliente);
        pedido.setIdProducto(producto);
        pedido.setCantidad(cantidad);
        pedido.setFecha(fecha);

        if (errores.isEmpty()) {
            service.guardar(pedido);
            resp.sendRedirect(req.getContextPath() + "/pedidos/listar");
        } else {
            req.setAttribute("errores", errores);
            getServletContext().getRequestDispatcher("/editarPedidos.jsp")
                    .forward(req, resp);
        }
    }
}
