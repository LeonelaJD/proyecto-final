<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="java.util.*" %>
<%@page import="java.time.format.*" %>


<%
 Map<String, String> errores = (Map<String, String>) request.getAttribute("errores");
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
            <div class="col-12">
                <h2>Formulario Agregar Clientes</h2>
            </div>

        </div>

        <br>
        <% if(errores != null && errores.size()>0) { %>
            <ul class="alert alert-danger mx-5 px-5">
                <% for (String error: errores.values()){ %>
                    <li>  <%=error%>  </li>
                <%}%>
            </ul>
        <%} %>

        <div class="row">

            <form action="<%=request.getContextPath()%>/clientes/agregar" method="post">
                <div class="col-md-12">

                    <div class="form-group">
                        <label for="">Id Cliente</label>
                        <input type="text" name="idCliente" id="idCliente"
                        class="form-control">
                        <%
                        if(errores != null && errores.containsKey("idCliente")){
                            out.println("<span class='text-danger'>"+ errores.get
                                ("idCliente") + "</span>");
                        }
                        %>
                    </div>

                    <div class="form-group">
                        <label for="">Nombre</label>
                        <input type="text" name="nombre" id="nombre"
                        class="form-control">
                        <%
                        if(errores != null && errores.containsKey("nombre")){
                            out.println("<span class='text-danger'>"+ errores.get
                                ("nombre") + "</span>");
                        }
                        %>
                    </div>

                    <div class="form-group">
                        <label for="">Rfc</label>
                        <input type="text" name="rfc" id="rfc"
                        class="form-control">
                        <%
                        if(errores != null && errores.containsKey("rfc")){
                            out.println("<span class='text-danger'>"+ errores.get
                                ("rfc") + "</span>");
                        }
                        %>
                    </div>
                    <div class="form-group">
                        <label for="">Telefono</label>
                        <input type="text" name="telefono" id="telefono"
                        class="form-control">
                        <%
                        if(errores != null && errores.containsKey("telefono")){
                            out.println("<span class='text-danger'>"+ errores.get
                                ("telefono") + "</span>");
                        }
                        %>
                    </div>
                    <div class="form-group">
                        <label for="">Direccion</label>
                        <input type="text" name="direccion" id="direccion"
                        class="form-control">
                        <%
                        if(errores != null && errores.containsKey("direccion")){
                            out.println("<span class='text-danger'>"+ errores.get
                                ("direccion") + "</span>");
                        }
                        %>
                    </div>

                    <div class="form-group">
                        <label for="">Razon Social</label>
                        <input type="text" name="razonSocial" id="razonSocial"
                        class="form-control">
                        <%
                        if(errores != null && errores.containsKey("razonSocial")){
                            out.println("<span class='text-danger'>"+ errores.get
                                ("razonSocial") + "</span>");
                        }
                        %>
                    </div>

                    <div class="form-group">
                        <label for="">Email</label>
                        <input type="text" name="email" id="email"
                        class="form-control">
                        <%
                        if(errores != null && errores.containsKey("email")){
                            out.println("<span class='text-danger'>"+ errores.get
                                ("email") + "</span>");
                        }
                        %>
                    </div>

                    <div class="form-group">
                        <button type="submit" class="btn btn-success">Guardar</button>

                    </div>


                </div>
            </form>
        </div>

    </div>

</body>
</html>