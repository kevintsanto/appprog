/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAcces;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import Clases.TipoRopa;
import Utilidades.ConexionBD;

/**
 * Esta clase representa un tipo con sus atributos correspondientes.
 *
 * @author gg
 */
public class DaTipoRopa {

    private Connection connection;

    public DaTipoRopa() throws SQLException {
        connection = ConexionBD.getConnection();
    }
public int obtenerMaxId() throws SQLException {
    String sql = "SELECT MAX(id_tipo) FROM tipo_ropa";
    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
            return rs.getInt(1);
        }
    }
    return 0; // Valor predeterminado si no se encuentra ningún ID en la tabla
}


 public List<String> obtenerNombres() {
        List<String> nombres = new ArrayList<>();

        String query = "SELECT nombre_tipo FROM tipo_ropa";

        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre_tipo");
                nombres.add(nombre);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nombres;
    }
    public boolean agregar(TipoRopa tipo) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO tipo_ropa (id_tipo, nombre_tipo) VALUES (?, ?)");
            ps.setInt(1, tipo.getId_tipo());
            ps.setString(2, tipo.getNombre_tipo());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // El tipo se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar el tipo de ropa", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar el tipo de ropa
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar el tipo de ropa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean actualizar(TipoRopa tipo) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE tipo_ropa SET nombre_tipo = ? WHERE id_tipo = ?");
            ps.setString(1, tipo.getNombre_tipo());
            ps.setInt(2, tipo.getId_tipo());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // El tipo se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar el tipo de ropa", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar el tipo de ropa
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el tipo de ropa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int id_tipo) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM tipo_ropa WHERE id_tipo = ?");
            ps.setInt(1, id_tipo);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // El tipo se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar el tipo de ropa", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar el tipo de ropa
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar el tipo de ropa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public TipoRopa obtenerPorId(int id_tipo) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tipo_ropa WHERE id_tipo = ?");
            ps.setInt(1, id_tipo);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de búsqueda
            if (rs.next()) {
                TipoRopa tipo = new TipoRopa();
                tipo.setId_tipo(rs.getInt("id_tipo"));
                tipo.setNombre_tipo(rs.getString("nombre_tipo"));

                return tipo; // Devolver el tipo encontrado
            } else {
                return null; // No se encontró ningún tipo con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener el tipo de ropa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<TipoRopa> obtenerTodos() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tipo_ropa");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de búsqueda
            List<TipoRopa> tipos = new ArrayList<>();
            while (rs.next()) {
                TipoRopa tipo = new TipoRopa();
                tipo.setId_tipo(rs.getInt("id_tipo"));
                tipo.setNombre_tipo(rs.getString("nombre_tipo"));

                tipos.add(tipo); // Agregar cada tipo encontrado a la lista
            }
            return tipos; // Devolver la lista de tipos encontrados
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener los tipos de ropa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public List<TipoRopa> buscar(String criterio) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM tipo_ropa WHERE nombre_tipo LIKE ?");
            ps.setString(1, "%" + criterio + "%");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de búsqueda
            List<TipoRopa> tipos = new ArrayList<>();
            while (rs.next()) {
                TipoRopa tipo = new TipoRopa();
                tipo.setId_tipo(rs.getInt("id_tipo"));
                tipo.setNombre_tipo(rs.getString("nombre_tipo"));

                tipos.add(tipo); // Agregar cada tipo encontrado a la lista
            }
            return tipos; // Devolver la lista de tipos encontrados
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar tipos de ropa: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
