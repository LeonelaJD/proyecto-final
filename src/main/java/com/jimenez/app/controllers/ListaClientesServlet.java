package com.jimenez.app.controllers;

import com.jimenez.app.models.Cliente;
import com.jimenez.app.models.Producto;
import com.jimenez.app.services.ClientesService;
import com.jimenez.app.services.IService;
import com.jimenez.app.services.ProductosService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/clientes/listar")
public class ListaClientesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //recuperamos la conexion que provee el filtro
        Connection conn = (Connection) req.getAttribute("conn");
        //declaramos un objeto de tipo servicio

        IService<Cliente> service = new ClientesService(conn);
        List<Cliente> clientes = service.listar();

        req.setAttribute("clientes", clientes);
        getServletContext().getRequestDispatcher("/listaClientes.jsp").forward(req, resp);
    }
}
