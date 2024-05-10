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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/proveedores/editar")
public class EditarProveedoresServlet extends HttpServlet {

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
                getServletContext().getRequestDispatcher("/editarProveedores.jsp")
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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Proveedor> service = new ProveedoresService(conn);

        long idProveedor = Long.parseLong(req.getParameter("id"));
        String nombre = req.getParameter("nombre");
        String rfc = req.getParameter("rfc");
        String descripcion = req.getParameter("descripcion");
        String razonSocial = req.getParameter("razonSocial");
        String direccion = req.getParameter("direccion");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");

        Map<String, String> errores = new HashMap<>();
        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "el nombre es requerido");
        }
        if (rfc == null || rfc.isBlank()) {
            errores.put("rfc", "el rfc es requerido");
        }
        if (descripcion == null || descripcion.isBlank()) {
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

        Proveedor proveedor = new Proveedor();
        proveedor.setIdProveedor(idProveedor);
        proveedor.setNombre(nombre);
        proveedor.setRfc(rfc);
        proveedor.setDescripcion(descripcion);
        proveedor.setRazonSocial(razonSocial);
        proveedor.setDireccion(direccion);
        proveedor.setEmail(email);
        proveedor.setTelefono(telefono);

        if (errores.isEmpty()) {
            service.guardar(proveedor);
            resp.sendRedirect(req.getContextPath() + "/proveedores/listar");
        } else {
            req.setAttribute("errores", errores);
            getServletContext().getRequestDispatcher("/editarProveedores.jsp")
                    .forward(req, resp);
        }
    }
}
