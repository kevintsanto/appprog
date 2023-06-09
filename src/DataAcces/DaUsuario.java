/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAcces;

import Utilidades.Encriptador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Clases.Usuario;
import Utilidades.ConexionBD;

/**
 * Esta clase representa un usuario con sus atributos correspondientes.
 *
 * @author gg
 */
public class DaUsuario {

    private Connection connection;

    public DaUsuario() throws SQLException {
        connection = ConexionBD.getConnection();
    }

public boolean agregar(Usuario usuario) {
    // Verificar si el usuario ya existe
    if (verificarExistencia(usuario.getEmail())) {
        JOptionPane.showMessageDialog(null, "El usuario ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO usuario (id_usuario, nombre, apellido, email, contraseña, tipo_usuario, permiso) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setInt(1, usuario.getId_usuario());
            ps.setString(2, usuario.getNombre());
            ps.setString(3, usuario.getApellido());
            ps.setString(4, usuario.getEmail());
            ps.setString(5, Encriptador.encriptar(usuario.getContraseña())); // Encriptar la contraseña antes de almacenarla en la base de datos
            ps.setString(6, usuario.getTipo_usuario());
            ps.setString(7, usuario.getPermiso());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // El usuario se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar el usuario
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    private boolean verificarExistencia(String email) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) AS count FROM usuario WHERE email = ?");
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt("count");
                return count > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean actualizar(Usuario usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE usuario SET nombre = ?, apellido = ?, email = ?, contraseña = ?, tipo_usuario = ?, permiso = ? WHERE id_usuario = ?");
            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getApellido());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, Encriptador.encriptar(usuario.getContraseña())); // Encriptar la contraseña antes de actualizarla
            ps.setString(5, usuario.getTipo_usuario());
            ps.setString(6, usuario.getPermiso());
            ps.setInt(7, usuario.getId_usuario());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // El usuario se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar el usuario
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int id_usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?");
            ps.setInt(1, id_usuario);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // El usuario se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar el usuario
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public Usuario obtenerPorId(int id_usuario) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");
            ps.setInt(1, id_usuario);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipo_usuario(rs.getString("tipo_usuario"));
                usuario.setPermiso(rs.getString("permiso"));

                return usuario; // Devolver el usuario encontrado
            } else {
                return null; // No se encontró ningún usuario con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<Usuario> obtenerTodos() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipo_usuario(rs.getString("tipo_usuario"));
                usuario.setPermiso(rs.getString("permiso"));

                usuarios.add(usuario); // Agregar cada usuario a la lista
            }
            return usuarios; // Devolver la lista de usuarios
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los usuarios: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<Usuario> buscar(String criterio) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM usuario WHERE nombre LIKE ? OR apellido LIKE ? OR email LIKE ?");
            ps.setString(1, "%" + criterio + "%");
            ps.setString(2, "%" + criterio + "%");
            ps.setString(3, "%" + criterio + "%");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de búsqueda
            List<Usuario> usuarios = new ArrayList<>();
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEmail(rs.getString("email"));
                usuario.setContraseña(rs.getString("contraseña"));
                usuario.setTipo_usuario(rs.getString("tipo_usuario"));
                usuario.setPermiso(rs.getString("permiso"));

                usuarios.add(usuario); // Agregar cada usuario encontrado a la lista
            }
            return usuarios; // Devolver la lista de usuarios encontrados
        } catch (SQLException ex) {
            System.out.println("Error al buscar usuarios: " + ex.getMessage());
            return null;
        }
    }
public int obtenerNuevoID() {
    int nuevoID = 0;
    try {
        PreparedStatement ps = connection.prepareStatement("SELECT MAX(id_usuario) FROM usuario");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nuevoID = rs.getInt(1) + 1;
        } else {
            nuevoID = 1;
        }
        rs.close();
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al obtener el nuevo ID del usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return nuevoID;
}


}
