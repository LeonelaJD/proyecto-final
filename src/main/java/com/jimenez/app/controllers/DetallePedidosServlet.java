package com.jimenez.app.controllers;

import com.jimenez.app.models.Pedido;
import com.jimenez.app.services.IService;
import com.jimenez.app.services.PedidosService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/pedidos/detalle")
public class DetallePedidosServlet extends HttpServlet {

    @Override
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
                getServletContext().getRequestDispatcher("/detallePedidos.jsp")
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
}
