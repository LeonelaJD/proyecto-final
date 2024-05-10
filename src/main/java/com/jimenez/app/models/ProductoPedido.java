package com.jimenez.app.models;

import java.time.LocalDate;

public class ProductoPedido {

    private Long idProductosPedido;
    private Long factura;
    private Long cliente;
    private Long producto;
    private Double cantidad;
    private LocalDate fecha;
    private String estatus;

    public ProductoPedido(){
    }

    //Gets and Sets

    public Long getIdProductosPedido() {
        return idProductosPedido;
    }

    public void setIdProductosPedido(Long idProductosPedido) {
        this.idProductosPedido = idProductosPedido;
    }

    public Long getFactura() {
        return factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public Long getCliente() {
        return cliente;
    }

    public void setCliente(Long cliente) {
        this.cliente = cliente;
    }

    public Long getProducto() {
        return producto;
    }

    public void setProducto(Long producto) {
        this.producto = producto;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
