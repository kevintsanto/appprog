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
import Clases.Marca;
import Utilidades.ConexionBD;

/**
 * Esta clase representa una marca con sus atributos correspondientes.
 *
 * @author gg
 */
public class DaMarca {

    private Connection connection;

    public DaMarca() throws SQLException {
        connection = ConexionBD.getConnection();
    }

    public boolean agregar(Marca marca) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO marca (id_marca, nombre_marca) VALUES (?, ?)");
            ps.setInt(1, marca.getId_marca());
            ps.setString(2, marca.getNombre_marca());

            int filasInsertadas = ps.executeUpdate(); // Ejecutar la consulta de inserción
            if (filasInsertadas > 0) {
                return true; // La marca se agregó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al agregar la marca", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al agregar la marca
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al agregar la marca: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }
public int obtenerNuevoID() {
    int nuevoID = 0;
    try {
        PreparedStatement ps = connection.prepareStatement("SELECT MAX(id_marca) FROM marca");
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            nuevoID = rs.getInt(1) + 1;
        } else {
            nuevoID = 1;
        }
        rs.close();
        ps.close();
    } catch (SQLException ex) {
        JOptionPane.showMessageDialog(null, "Error al obtener el nuevo ID de la marca: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    return nuevoID;
}



    public boolean actualizar(Marca marca) {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE marca SET nombre_marca = ? WHERE id_marca = ?");
            ps.setString(1, marca.getNombre_marca());
            ps.setInt(2, marca.getId_marca());

            int filasActualizadas = ps.executeUpdate(); // Ejecutar la consulta de actualización
            if (filasActualizadas > 0) {
                return true; // La marca se actualizó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al actualizar la marca", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al actualizar la marca
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al actualizar la marca: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public boolean eliminar(int id_marca) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM marca WHERE id_marca = ?");
            ps.setInt(1, id_marca);

            int filasEliminadas = ps.executeUpdate(); // Ejecutar la consulta de eliminación
            if (filasEliminadas > 0) {
                return true; // La marca se eliminó correctamente
            } else {
                JOptionPane.showMessageDialog(null, "Error al eliminar la marca", "Error", JOptionPane.ERROR_MESSAGE);
                return false; // Hubo un error al eliminar la marca
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la marca: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false; // Hubo un error al ejecutar la consulta
        }
    }

    public Marca obtenerPorId(int id_marca) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM marca WHERE id_marca = ?");
            ps.setInt(1, id_marca);

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            if (rs.next()) {
                Marca marca = new Marca();
                marca.setId_marca(rs.getInt("id_marca"));
                marca.setNombre_marca(rs.getString("nombre_marca"));

                return marca; // Devolver la marca encontrada
            } else {
                return null; // No se encontró ninguna marca con el ID especificado
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener la marca: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }
    }

    public List<Marca> obtenerTodas() {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM marca");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de selección
            List<Marca> marcas = new ArrayList<>();
            while (rs.next()) {
                Marca marca = new Marca();
                marca.setId_marca(rs.getInt("id_marca"));
                marca.setNombre_marca(rs.getString("nombre_marca"));

                marcas.add(marca); // Agregar cada marca a la lista
            }
            return marcas; // Devolver la lista de marcas
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al obtener las marcas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null; // Hubo un error al ejecutar la consulta
        }

    }

    public List<Marca> buscar(String criterio) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM marca WHERE nombre_marca LIKE ?");
            ps.setString(1, "%" + criterio + "%");

            ResultSet rs = ps.executeQuery(); // Ejecutar la consulta de búsqueda
            List<Marca> marcas = new ArrayList<>();
            while (rs.next()) {
                Marca marca = new Marca();
                marca.setId_marca(rs.getInt("id_marca"));
                marca.setNombre_marca(rs.getString("nombre_marca"));

                marcas.add(marca); // Agregar cada marca encontrada a la lista
            }
            return marcas; // Devolver la lista de marcas encontradas
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al buscar marcas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
}
