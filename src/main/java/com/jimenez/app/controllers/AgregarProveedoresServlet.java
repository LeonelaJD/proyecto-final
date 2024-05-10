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

@WebServlet("/proveedores/agregar")
public class AgregarProveedoresServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/agregarProveedores.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Proveedor> service = new ProveedoresService(conn);

        String nombre = req.getParameter("nombre");
        String rfc = req.getParameter("rfc");
        String descripcion = req.getParameter("descripcion");
        String razonSocial = req.getParameter("razonSocial");
        String direccion = req.getParameter("direccion");
        String email = req.getParameter("email");
        String telefono = req.getParameter("telefono");

        Map<String, String> errores = new HashMap<>();
        if (nombre == null || nombre.isBlank()) {
            errores.put("proveedor", "el proveedor es requerido!");
        }
        if (rfc == null || rfc.isBlank()) {
            errores.put("categoria", "categoria es requerido!");
        }
        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "el nombre es requerido!");
        }
        if (descripcion == null || descripcion.isBlank()) {
            errores.put("descripcion", "descripcion es requerido!");
        }
        if (razonSocial == null || razonSocial.isBlank()) {
            errores.put("precioUnitario", "precioUnitario es requerida!");
        }
        if (direccion == null || direccion.isBlank()) {
            errores.put("precioCompra", "precioCompra es requerida!");
        }
        if (email == null || email.isBlank()) {
            errores.put("existencia", "existencia es requerida!");
        }
        if (telefono == null || telefono.isBlank()) {
            errores.put("telefono", "telefono es requerido!");
        }
        if (errores.isEmpty()) {
            Proveedor proveedor = new Proveedor();
            proveedor.setNombre(nombre);
            proveedor.setRfc(rfc);
            proveedor.setDescripcion(descripcion);
            proveedor.setRazonSocial(razonSocial);
            proveedor.setDireccion(direccion);
            proveedor.setEmail(email);
            proveedor.setTelefono(telefono);
            service.guardar(proveedor);
            resp.sendRedirect(req.getContextPath()+ "/proveedores/listar");
        }
        else {
            req.setAttribute("errores", errores);
            getServletContext().getRequestDispatcher("/agregarProveedor.jsp")
                    .forward(req, resp);
        }
    }
}



