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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/pedidos/agregar")
public class AgregarPedidosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/agregarPedidos.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) req.getAttribute("conn");
        IService<Pedido> service = new PedidosService(conn);

        Long cliente = Long.valueOf(req.getParameter("cliente"));
        LocalDate fecha = LocalDate.parse(req.getParameter("fecha"));
        String cantidadPapel = req.getParameter("cantidad_PAPEL_BOND");
        String cantidadPinturas = req.getParameter("cantidad_PINTURAS_ACRILICAS");
        String cantidadCarpetas = req.getParameter("cantidad_CARPETAS_OFICIO");

        Map<String, String> errores = new HashMap<>();
        if (cliente == null) {
            errores.put("cliente", "cliente es requerido!");
        }
        if (cantidadPapel == null && cantidadPinturas == null && cantidadCarpetas == null) {
            errores.put("productos", "al menos un producto es requerido!");
        }
        if (fecha == null) {
            errores.put("fecha", "fecha es requerida!");
        }

        if (errores.isEmpty()) {
            if (!cantidadPapel.isBlank()) {
                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                pedido.setIdProducto(4L);
                pedido.setFecha(fecha);
                pedido.setCantidad(Long.valueOf(cantidadPapel));
                service.guardar(pedido);
            }
            if (!cantidadPinturas.isBlank()) {
                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                pedido.setIdProducto(4L);
                pedido.setFecha(fecha);
                pedido.setCantidad(Long.valueOf(cantidadPinturas));
                service.guardar(pedido);
            }
            if (!cantidadCarpetas.isBlank()) {
                Pedido pedido = new Pedido();
                pedido.setCliente(cliente);
                pedido.setIdProducto(4L);
                pedido.setFecha(fecha);
                pedido.setCantidad(Long.valueOf(cantidadCarpetas));
                service.guardar(pedido);
            }
            resp.sendRedirect(req.getContextPath()+ "/pedidos/listar");
        }
        else {
            req.setAttribute("errores", errores);
            getServletContext().getRequestDispatcher("/agregarPedido.jsp")
                    .forward(req, resp);
        }
    }
}



