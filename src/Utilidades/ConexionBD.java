/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gg
 */
public class ConexionBD {

    private static Connection conn = null;

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            String url = "jdbc:mysql://localhost:3306/tachisetino?useSSL=false";
            String usuario = "root";
            String contraseña = "root";

            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(url, usuario, contraseña);
            } catch (ClassNotFoundException e) {
                throw new SQLException("Error al cargar el driver de MySQL.");
            } catch (SQLException e) {
                throw new SQLException("Error al establecer la conexión a la base de datos.");
            }
        }

        return conn;
    }

    public static void main(String[] args) {
        Connection connection = null;
        try {
            //Se carga el driver de Mysql
            Class.forName("com.mysql.jdbc.Driver");
            //Paso 2: establecer la ConexionBD
            String url = "jdbc:mysql://localhost:3306/tachisetino";
            String usuario = "root";
            String contraseña = "root";
            connection = DriverManager.getConnection(url, usuario, contraseña);
            if (connection != null) {
                System.out.println("Conexión exitosa a la base de datos.");
            } else {
                System.out.println("No se pudo establecer la conexión a la base de datos.");
            }
            // Realizamos operaciones con la ConexionBD
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
