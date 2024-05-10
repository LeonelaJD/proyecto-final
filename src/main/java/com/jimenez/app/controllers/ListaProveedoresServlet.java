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
import java.util.List;

@WebServlet("/proveedores/listar")
public class ListaProveedoresServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //recuperamos la conexion que provee el filtro
        Connection conn = (Connection) req.getAttribute("conn");
        //declaramos un objeto de tipo servicio

        IService<Proveedor> service = new ProveedoresService(conn);
        List<Proveedor> proveedores = service.listar();

        req.setAttribute("proveedores", proveedores);
        getServletContext().getRequestDispatcher("/listaProveedores.jsp").forward(req, resp);
    }
}
