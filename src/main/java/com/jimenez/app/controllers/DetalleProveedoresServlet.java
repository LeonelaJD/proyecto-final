package com.jimenez.app.controllers;

import com.jimenez.app.models.Proveedor;
import com.jimenez.app.services.IService;
import com.jimenez.app.services.ProveedoresService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/proveedores/detalle")
public class DetalleProveedoresServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Proveedor> service = new ProveedoresService(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));//333
        } catch (NumberFormatException e) {
            id = 0L;
        }
        Proveedor proveedor;
        if (id > 0) {
            Optional<Proveedor> o = service.getById(id);
            if (o.isPresent()) {
                proveedor = o.get();
                req.setAttribute("proveedor", proveedor);
                getServletContext().getRequestDispatcher("/detalleProveedores.jsp")
                        .forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No existe el proveedor en la base de datos!");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Error, el id es null, se debe enviar como parametro en la url!");
        }
    }
}
