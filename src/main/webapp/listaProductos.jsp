<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>
<%@page import="com.jimenez.app.models.*" %>

<%
 //recuperamos la lista de choferes que seteamos en el request desde el servlet
 List<Producto> productos =  (List<Producto>) request.getAttribute("productos");
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script src="https://code.jquery.com/jquery-2.2.4.min.js"
      integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
      crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="//cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
<script src="//cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header" id="div1">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                        <a class="navbar-brand" href="#" id="enlace1">App. Inventario Productos</a>
            </div>
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-haspopup="true" aria-expanded="false">Productos<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=request.getContextPath()%>/productos/listar">Productos</a></li>

                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-haspopup="true" aria-expanded="false">Proveedores<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=request.getContextPath()%>/proveedores/listar">Proveedores</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                            aria-haspopup="true" aria-expanded="false">Clientes<span
                                class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="<%=request.getContextPath()%>/clientes/listar">Clientes</a></li>
                        </ul>
                    </li>
                    <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button"
                        aria-haspopup="true" aria-expanded="false">Pedidos<span
                            class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="<%=request.getContextPath()%>/pedidos/listar">Pedidos</a></li>
                    </ul>
                </li>

                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>

    <div class="container">
        <div class="row">
            <div class="col-6">
                <h2>Lista de Productos</h2>
            </div>
            <div class="col-6">
              <a href="<%=request.getContextPath()%>/productos/agregar" class="btn btn-success">Agregar Producto</a>
            </div>
        </div>

        <div class="row">
            <div class="col-12">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped" width="100" cellspacing="0">
                        <thead>
                            <tr>
                                <th>Id</th>
                                <th>Nombre</th>
                                <th>Precio Unitario</th>
                                <th>Existencia</th>
                                <th colspan="4" style="text-align:center;">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(Producto producto: productos) {%>
                                <tr>
                                    <td>  <%=producto.getIdProducto()%>  </td>
                                    <td>  <%=producto.getNombre()%>  </td>
                                    <td>  <%=producto.getPrecioUnitario()%>  </td>
                                    <td>  <%=producto.getExistencia()%>  </td>
                                    <td><a href="<%=request.getContextPath()%>/productos/detalle?id=<%=producto.getIdProducto()%>" class="btn btn-success">Detalle</a></td>
                                    <td><a href="<%=request.getContextPath()%>/productos/editar?id=<%=producto.getIdProducto()%>" class="btn btn-primary">Editar</a></td>
                                    <td><a href="<%=request.getContextPath()%>/productos/eliminar?id=<%=producto.getIdProducto()%>" class="btn btn-danger">Eliminar</a></td>
                                    <td><button class="btn btn-success" onclick="btnConsumirApi('<%=producto.getPrecioUnitario()%>');">Ver precio en Dólares</button></div></td>
                                </tr>

                                <% } %>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

<script>

function btnConsumirApi(precio){
   fetch(`https://api.exchangerate-api.com/v4/latest/MXN`).then(
            res => res.json()
   ).then(data => {
        console.log(data);
        var cambio = data.rates.USD;
        alert("El precio en dólares es: " + cambio * precio);
   });
}

</script>

</body>
</html>