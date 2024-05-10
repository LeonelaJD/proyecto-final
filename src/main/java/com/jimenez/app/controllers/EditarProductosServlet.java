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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@WebServlet("/productos/editar")
public class EditarProductosServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Producto> service = new ProductosService(conn);

        long id;
        try {
            id = Long.parseLong(req.getParameter("id"));//333
        } catch (NumberFormatException e) {
            id = 0L;
        }
        Producto producto = new Producto();
        if (id > 0) {
            Optional<Producto> o = service.getById(id);
            if (o.isPresent()) {
                producto = o.get();
                req.setAttribute("producto", producto);
                getServletContext().getRequestDispatcher("/editarProductos.jsp")
                        .forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                        "No existe el producto en la base de datos!");
            }
        } else {
            //resp.sendRedirect(req.getContextPath() + "/choferes/lsta");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "Error, el id es null, se debe enviar como parametro en la url!");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Producto> service = new ProductosService(conn);

        Long idProducto = Long.valueOf(req.getParameter("idProducto"));
        Long proveedor = Long.valueOf(req.getParameter("proveedor"));
        Long categoria = Long.valueOf(req.getParameter("categoria"));
        String nombre = req.getParameter("nombre");
        String descripcion = req.getParameter("descripcion");
        Double precioUnitario = Double.valueOf(req.getParameter("precioUnitario"));
        Double precioCompra = Double.valueOf(req.getParameter("precioCompra"));
        Double existencia = Double.valueOf(req.getParameter("existencia"));

        /*LocalDate fecha;
        try {
            fecha = LocalDate.parse(fechaNacimiento, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeException e) {
            fecha = null;
        }*/

        String checkbox[];
        checkbox = req.getParameterValues("existencia");
        Boolean habilitar;
        if (checkbox != null) {
            habilitar = true;
        } else {
            habilitar = false;
        }
        Map<String, String> errores = new HashMap<>();
        if (nombre == null || nombre.isBlank()) {
            errores.put("nombre", "el nombre es requerido");
        }
        if (idProducto == null) {
            errores.put("idProducto", "el idProducto es requerido!");
        }
        if (proveedor == null) {
            errores.put("proveedor", "el proveedor es requerido!");
        }
        if (categoria == null) {
            errores.put("categoria", "la categoria es requerida!");
        }
        if (descripcion == null) {
            errores.put("descripcion", "la descripcion es requerido!");
        }
        if (precioUnitario == null) {
            errores.put("precioUnitario", "precioUnitario es requerida!");
        }
        if (precioCompra == null) {
            errores.put("precioCompra", "precioCompra es requerida!");
        }
        if (existencia == null) {
            errores.put("existencia", "existencia es requerida!");
        }

        long id;
        id = Long.parseLong(req.getParameter("id")); //323
        Producto producto = new Producto();
        producto.setIdProducto(idProducto);
        producto.setNombre(nombre);
        producto.setProveedor(proveedor);
        producto.setCategoria(categoria);
        producto.setDescripcion(descripcion);
        producto.setPrecioUnitario(precioUnitario);
        producto.setPrecioCompra(precioCompra);
        producto.setExistencia(existencia);

        if (errores.isEmpty()) {

            service.guardar(producto);
            resp.sendRedirect(req.getContextPath() + "/productos/listar");
        } else {
            req.setAttribute("errores", errores);
            getServletContext().getRequestDispatcher("/editarProductos.jsp")
                    .forward(req, resp);
        }
    }
}
