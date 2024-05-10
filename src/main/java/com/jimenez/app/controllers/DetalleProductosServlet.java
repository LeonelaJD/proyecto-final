package com.jimenez.app.controllers;

import com.jimenez.app.models.Producto;
import com.jimenez.app.services.IService;
import com.jimenez.app.services.ProductosService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.Optional;

@WebServlet("/productos/detalle")
public class DetalleProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Producto> service = new ProductosService(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));//333
        } catch (NumberFormatException e) {
            id = 0L;
        }
        Producto producto;
        if (id > 0) {
            Optional<Producto> o = service.getById(id);
            if (o.isPresent()) {
                producto = o.get();
                req.setAttribute("producto", producto);
                getServletContext().getRequestDispatcher("/detalleProductos.jsp")
                        .forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No existe el producto en la base de datos!");
            }
        } else {
            //resp.sendRedirect(req.getContextPath() + "/productos/lista");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Error, el id es null, se debe enviar como parametro en la url!");
        }
    }
}
