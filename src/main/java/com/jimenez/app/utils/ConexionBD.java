package com.jimenez.app.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    //Atributos
    private static String url = "jdbc:oracle:thin:@//127.0.0.1:1521/xe";
    private static String username = "SYSTEM";
    private static String password = "admin";

    //metodos
    //metodo que establece la conexion al servdor de BD oracle

    public static Connection getInstance() {
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

