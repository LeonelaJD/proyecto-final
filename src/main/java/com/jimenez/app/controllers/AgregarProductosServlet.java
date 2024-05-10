package com.jimenez.app.controllers;

import com.jimenez.app.models.Categorias;
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

@WebServlet("/productos/agregar")
public class AgregarProductosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    getServletContext().getRequestDispatcher("/agregarProductos.jsp")
            .forward(req, resp);
}

@Override
protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Connection conn = (Connection) req.getAttribute("conn");
    IService<Producto> service = new ProductosService(conn);

    Long proveedor = Long.valueOf(req.getParameter("proveedor"));
    String categoria = req.getParameter("categoria");
    String nombre = req.getParameter("nombre");
    String descripcion = req.getParameter("descripcion");
    Double precioUnitario = Double.valueOf(req.getParameter("precioUnitario"));
    Double precioCompra = Double.valueOf(req.getParameter("precioCompra"));
    Double existencia = Double.valueOf(req.getParameter("existencia"));

    Map<String, String> errores = new HashMap<>();
    if (proveedor == null || proveedor == 0) {
        errores.put("proveedor", "el proveedor es requerido!");
    }
    if (categoria == null || categoria.isBlank()) {
        errores.put("categoria", "categoria es requerido!");
    }
    if (nombre == null || nombre.isBlank()) {
        errores.put("nombre", "el nombre es requerido!");
    }
    if (descripcion == null || descripcion.isBlank()) {
        errores.put("descripcion", "descripcion es requerido!");
    }
    if (precioUnitario == null || precioUnitario == 0) {
        errores.put("precioUnitario", "precioUnitario es requerida!");
    }
    if (precioCompra == null || precioCompra == 0) {
        errores.put("precioCompra", "precioCompra es requerida!");
    }
    if (existencia == null || existencia == 0) {
        errores.put("existencia", "existencia es requerida!");
    }
    if (errores.isEmpty()) {
        Producto producto = new Producto();
        producto.setProveedor(proveedor);
        long idCategoria = Categorias.valueOf(categoria).getId();
        producto.setCategoria(idCategoria);
        producto.setNombre(nombre);
        producto.setDescripcion(descripcion);
        producto.setPrecioUnitario(precioUnitario);
        producto.setPrecioCompra(precioCompra);
        producto.setExistencia(existencia);
        service.guardar(producto);
        resp.sendRedirect(req.getContextPath()+ "/productos/listar");
    }
    else {
        req.setAttribute("errores", errores);
        getServletContext().getRequestDispatcher("/agregarProductos.jsp")
                .forward(req, resp);
        }
    }
}



