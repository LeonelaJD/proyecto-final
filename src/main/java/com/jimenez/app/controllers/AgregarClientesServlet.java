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
import java.util.HashMap;
import java.util.Map;

@WebServlet("/clientes/agregar")
public class AgregarClientesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/agregarClientes.jsp")
            .forward(req, resp);
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Connection conn = (Connection) req.getAttribute("conn");
    IService<Cliente> service = new ClientesService(conn);

    Long idCliente = Long.valueOf(req.getParameter("idCliente"));
    String nombre = req.getParameter("nombre");
    String rfc = req.getParameter("rfc");
    String telefono = req.getParameter("telefono");
    String direccion = req.getParameter("direccion");
    String razonSocial = req.getParameter("razonSocial");
    String email = req.getParameter("email");

    Map<String, String> errores = new HashMap<>();
    if (idCliente == null || idCliente == 0) {
        errores.put("idCliente", "el idCliente es requerido!");
    }
    if (rfc == null || rfc.isBlank()) {
        errores.put("rfc", "rfc es requerido!");
    }
    if (nombre == null || nombre.isBlank()) {
        errores.put("nombre", "el nombre es requerido!");
    }
    if (telefono == null || telefono.isBlank()) {
        errores.put("telefono", "telefono es requerido!");
    }
    if (direccion == null || direccion.isBlank()) {
        errores.put("direccion", "direccion es requerida!");
    }
    if (razonSocial == null || razonSocial.isBlank()) {
        errores.put("razonSocial", "razonSocial es requerida!");
    }
    if (email == null || email.isBlank()) {
        errores.put("email", "email es requerida!");
    }
    if (errores.isEmpty()) {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setRfc(rfc);
        cliente.setNombre(telefono);
        cliente.setDireccion(direccion);
        cliente.setRazonSocial(razonSocial);
        cliente.setEmail(email);
        service.guardar(cliente);
        resp.sendRedirect(req.getContextPath()+ "/clientes/listar");
    }
    else {
        req.setAttribute("errores", errores);
        getServletContext().getRequestDispatcher("/agregarClientes.jsp")
                .forward(req, resp);
    }
}
}



