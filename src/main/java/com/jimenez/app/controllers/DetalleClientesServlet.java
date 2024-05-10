package com.jimenez.app.controllers;

import com.jimenez.app.models.Cliente;
import com.jimenez.app.services.ClientesService;
import com.jimenez.app.services.IService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/clientes/detalle")
public class DetalleClientesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Cliente> service = new ClientesService(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));//333
        } catch (NumberFormatException e) {
            id = 0L;
        }
        Cliente cliente;
        if (id > 0) {
            Optional<Cliente> o = service.getById(id);
            if (o.isPresent()) {
                cliente = o.get();
                req.setAttribute("cliente", cliente);
                getServletContext().getRequestDispatcher("/detalleClientes.jsp")
                        .forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No existe el cliente en la base de datos!");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Error, el id es null, se debe enviar como parametro en la url!");
        }
    }
}
