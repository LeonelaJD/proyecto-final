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
import java.util.List;

@WebServlet("/pedidos/listar")
public class ListaPedidosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //recuperamos la conexion que provee el filtro
        Connection conn = (Connection) req.getAttribute("conn");
        //declaramos un objeto de tipo servicio

        IService<Pedido> service = new PedidosService(conn);
        List<Pedido> pedidos = service.listar();

        req.setAttribute("pedidos", pedidos);
        getServletContext().getRequestDispatcher("/listaPedidos.jsp").forward(req, resp);
    }
}
