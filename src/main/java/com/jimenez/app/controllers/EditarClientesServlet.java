package com.jimenez.app.controllers;

import com.jimenez.app.models.Cliente;
import com.jimenez.app.models.Proveedor;
import com.jimenez.app.services.ClientesService;
import com.jimenez.app.services.IService;
import com.jimenez.app.services.ProveedoresService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/clientes/editar")
public class EditarClientesServlet extends HttpServlet {

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
                getServletContext().getRequestDispatcher("/editarClientes.jsp")
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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Cliente> service = new ClientesService(conn);
        Long idCliente = Long.parseLong(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String rfc = req.getParameter("rfc");
        String telefono = req.getParameter("telefono");
        String direccion = req.getParameter("direccion");
        String razonSocial = req.getParameter("razonSocial");
        String email = req.getParameter("email");

        Map<String, String> errores = new HashMap<>();
        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "el nombre es requerido");
        }
        if (rfc == null || rfc.isBlank()) {
            errores.put("rfc", "el rfc es requerido");
        }
        if (idCliente == null) {
            errores.put("descripcion", "la descripcion es requerida");
        }
        if (razonSocial == null || razonSocial.isBlank()) {
            errores.put("razonSocial", "la razon social es requerida");
        }
        if (direccion == null || direccion.isBlank()) {
            errores.put("direccion", "la direccion es requerida");
        }
        if (email == null || email.isBlank()) {
            errores.put("email", "el email es requerido");
        }
        if (telefono == null || telefono.isBlank()) {
            errores.put("telefono", "el telefono es requerido");
        }

        Cliente cliente = new Cliente();
        cliente.setIdCliente(idCliente);
        cliente.setNombre(nombre);
        cliente.setRfc(rfc);
        cliente.setRazonSocial(razonSocial);
        cliente.setDireccion(direccion);
        cliente.setEmail(email);
        cliente.setTelefono(telefono);

        if (errores.isEmpty()) {
            service.guardar(cliente);
            resp.sendRedirect(req.getContextPath() + "/clientes/listar");
        } else {
            req.setAttribute("errores", errores);
            getServletContext().getRequestDispatcher("/editarClientes.jsp")
                    .forward(req, resp);
        }
    }
}
