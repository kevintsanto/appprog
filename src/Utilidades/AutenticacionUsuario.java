package Utilidades;

import interfazGrafica.jFormMenuPrincipal;
import DataAcces.DaUsuario;
import Clases.Usuario;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AutenticacionUsuario {

    private DaUsuario userDao;
    private Connection connection;

    public AutenticacionUsuario() throws SQLException {
        connection = ConexionBD.getConnection();
        userDao = new DaUsuario();
    }

    public AutenticacionUsuario(DaUsuario userDao) {
        this.userDao = userDao;
    }

    public boolean inicio(String usuario, String contraseña) {
        Usuario usuarioEncontrado = userDao.obtenerPorNombreUsuario(usuario);

        if (usuarioEncontrado == null) {
            JOptionPane.showMessageDialog(null, "Usuario no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Usuario no encontrado en la base de datos
        }

        String contraseñaEncriptada = Encriptador.encriptar(contraseña);

        if (!usuarioEncontrado.getContraseña().equals(contraseñaEncriptada)) {
            JOptionPane.showMessageDialog(null, "Credenciales inválidas.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Credenciales inválidas
        }

        if (!estaEnBaseDeDatos(usuarioEncontrado)) {
            JOptionPane.showMessageDialog(null, "El usuario no está registrado en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Usuario no registrado en la base de datos
        }

        // Sesión iniciada correctamente, realizar acciones adicionales aquí
        JOptionPane.showMessageDialog(null, "Inicio de sesión exitoso.", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        jFormMenuPrincipal menuPrincipal = new jFormMenuPrincipal();
        menuPrincipal.setVisible(true);
        return true; // Sesión iniciada correctamente
    }

    private boolean estaEnBaseDeDatos(Usuario usuario) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean estaRegistrado = false;

        try {
            String tableName = "usuario";  // Nombre de la tabla

            String query = "SELECT * FROM " + tableName + " WHERE nombre = ? AND contraseña = ?";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, Encriptador.encriptar(usuario.getContraseña()));
            rs = stmt.executeQuery();

            estaRegistrado = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return estaRegistrado;
    }

    public boolean registro(Usuario usuario) {
        return userDao.agregar(usuario);
    }
}
